package com.example.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.FileHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;


@Component
public class Utils {
	private static final String PREFIX = "data/xquery/";
	
	private static final String NAUCNI_RAD_GRAPH_URI = "example/naucni_rad/metadata";
	@Autowired
	protected DatabaseClient client;
	


	public String readQuery(String queryName) throws IOException {
		String filePath = PREFIX + queryName;
		return readFile(filePath, StandardCharsets.UTF_8);
	}

	public String getResponse(String query) {
		StringBuilder bld = new StringBuilder();

		ServerEvaluationCall invoker = client.newServerEval();
		invoker.xquery(query);
		EvalResultIterator response = invoker.eval();

		if (response.hasNext()) {
			for (EvalResult result : response) {
				bld.append("\n" + result.getString());
			}
		} else {
			System.out.println("your query returned an empty sequence.");
		}
		return bld.toString();
	}
	
	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	private TransformerFactory transformerFactory;

	private static final String XSLT_FILE = "data/xsl/grddl.xsl";
	
	public void extractMetadata(InputStream in, OutputStream out) throws FileNotFoundException, TransformerException {
		
		transformerFactory = new TransformerFactoryImpl();
		
		// Create transformation source
		StreamSource transformSource = new StreamSource(new File(XSLT_FILE));
		
		// Initialize GRDDL transformer object
		Transformer grddlTransformer = transformerFactory.newTransformer(transformSource);
		
		// Set the indentation properties
		grddlTransformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		grddlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		// Initialize transformation subject
		StreamSource source = new StreamSource(in);

		// Initialize result stream
		StreamResult result = new StreamResult(out);
		
		// Trigger the transformation
		grddlTransformer.transform(source, result);
		
	}
	
	static public class ConnectionProperties {

		public String host;
		public int port = -1;
		public String user;
		public String password;
		public String database;
		public Authentication authType;

		public ConnectionProperties(Properties props) {
			super();
			host = props.getProperty("conn.host").trim();
			port = Integer.parseInt(props.getProperty("conn.port"));
			user = props.getProperty("conn.user").trim();
			password = props.getProperty("conn.password").trim();
			database = props.getProperty("conn.database").trim();
			authType = Authentication.valueOf(props.getProperty("conn.authentication_type").toUpperCase().trim());
		}
	}

	/**
	 * Read the configuration properties for the example.
	 * 
	 * @return the configuration object
	 */
	public static ConnectionProperties loadProperties() throws IOException {
		String propsName = "connection.properties";

		InputStream propsStream = openStream(propsName);
		if (propsStream == null)
			throw new IOException("Could not read properties " + propsName);

		Properties props = new Properties();
		props.load(propsStream);

		return new ConnectionProperties(props);
	}

	/**
	 * Read a resource for an example.
	 * 
	 * @param fileName
	 *            the name of the resource
	 * @return an input stream for the resource
	 * @throws IOException
	 */
	
	private static DatabaseClient client2;
	public static InputStream openStream(String fileName) throws IOException {
		return Utils.class.getClassLoader().getResourceAsStream(fileName);
	}
	
	public static void writeRDFnr(ConnectionProperties props, String rdfFilePath) throws IOException {
				
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client2 = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client2 = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		// Create a document manager to work with XML files.
		GraphManager graphManager = client2.newGraphManager();
		
		// Set the default media type (RDF/XML)
		graphManager.setDefaultMimetype(RDFMimeTypes.RDFXML);
		

		// A handle to hold the RDF content.
		FileHandle rdfFileHandle =
				new FileHandle(new File(rdfFilePath))
				.withMimetype(RDFMimeTypes.RDFXML);
		
		// Write the document to the database
		System.out.println("[INFO] Loading triples from \"" + rdfFilePath + "\"\n\n" + FileUtils.readFile(rdfFilePath, UTF_8));
		
		// Using a named graph to write the RDF file contents
		// To reference the default graph use GraphManager.DEFAULT_GRAPH.
		
		// Writing the first named graph
		graphManager.write(NAUCNI_RAD_GRAPH_URI, rdfFileHandle);
		
		
		// Release the client
		client2.release();
		
		System.out.println("[INFO] End.");
	}

}
