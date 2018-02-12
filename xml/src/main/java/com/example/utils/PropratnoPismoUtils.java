package com.example.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.example.model.propratnopismo.ProptatnoPismo;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.io.FileHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;

@Component
public class PropratnoPismoUtils {
	private JAXBContext getPropratnoPismoContext() throws JAXBException {
		return JAXBContext.newInstance(ProptatnoPismo.class);
	}

	public ProptatnoPismo unmarshalling(String file) throws JAXBException {
		Unmarshaller unmarshaller = this.getPropratnoPismoContext().createUnmarshaller();
		return (ProptatnoPismo) unmarshaller.unmarshal(new File("./upload-dir/" + file));
	}

	public String marshalling(ProptatnoPismo pismo) throws JAXBException {
		Marshaller marshaller = this.getPropratnoPismoContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(pismo, sw);
		return sw.toString();
	}

	public void validation(String pismo) throws JAXBException, SAXException {
		Unmarshaller unmarshaller = this.getPropratnoPismoContext().createUnmarshaller();
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("data/xsd/protpratno_pismo.xsd"));

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		StringReader reader = new StringReader(pismo);
		unmarshaller.unmarshal(reader);
	}

	public static void writeRDF( String rdfFilePath) throws IOException {
		
		DatabaseClient client2= DatabaseClientFactory.newClient("localhost", 8000, "admin", "admin",
				DatabaseClientFactory.Authentication.DIGEST);
		
		// Create a document manager to work with XML files.
		GraphManager graphManager = client2.newGraphManager();
		
		// Set the default media type (RDF/XML)
		graphManager.setDefaultMimetype(RDFMimeTypes.RDFXML);
		

		// A handle to hold the RDF content.
		FileHandle rdfFileHandle =
				new FileHandle(new File(rdfFilePath))
				.withMimetype(RDFMimeTypes.RDFXML);
		
		// Write the document to the database
		System.out.println("[INFO] Loading triples from \"" + rdfFilePath + "\"\n\n" + FileUtils.readFile("gen/rdf/"+rdfFilePath+".rdf", UTF_8));
		
		// Using a named graph to write the RDF file contents
		// To reference the default graph use GraphManager.DEFAULT_GRAPH.
		
		// Writing the first named graph
		graphManager.merge("example/protpatno_pismo/"+rdfFilePath+"/metadata", rdfFileHandle);
		
		
		// Release the client
		client2.release();
		
		System.out.println("[INFO] End.");
	}
	}
