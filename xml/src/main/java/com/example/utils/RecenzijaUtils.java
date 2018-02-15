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

import com.example.model.recenzija.Recenzija;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.io.FileHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;

@Component
public class RecenzijaUtils {

	private JAXBContext getRecenzijaContext() throws JAXBException {
		return JAXBContext.newInstance(Recenzija.class);
	}

	public Recenzija unmarshalling(String file) throws JAXBException {
		Unmarshaller unmarshaller = this.getRecenzijaContext().createUnmarshaller();
		return (Recenzija) unmarshaller.unmarshal(new File("./upload-dir/" + file));
	}

	public String marshalling(Recenzija recenzija) throws JAXBException {
		Marshaller marshaller = this.getRecenzijaContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/naucni_rad", "nr"));
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/uloge", "ulog"));
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(recenzija, sw);
		return sw.toString();
	}

	public void validation(String recenzija) throws JAXBException, SAXException {
		Unmarshaller unmarshaller = this.getRecenzijaContext().createUnmarshaller();
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("data/xsd/recenzija.xsd"));

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		StringReader reader = new StringReader(recenzija);
		unmarshaller.unmarshal(reader);
	}

	public static void writeRDF(String rdfFilePath) throws IOException {

		DatabaseClient client2 = DatabaseClientFactory.newClient("localhost", 8000, "admin", "admin",
				DatabaseClientFactory.Authentication.DIGEST);

		// Create a document manager to work with XML files.
		GraphManager graphManager = client2.newGraphManager();

		// Set the default media type (RDF/XML)
		graphManager.setDefaultMimetype(RDFMimeTypes.RDFXML);

		// A handle to hold the RDF content.
		FileHandle rdfFileHandle = new FileHandle(new File(rdfFilePath)).withMimetype(RDFMimeTypes.RDFXML);

		// Write the document to the database
		System.out.println("[INFO] Loading triples from \"" + rdfFilePath + "\"\n\n"
				+ FileUtils.readFile("gen/rdf/" + rdfFilePath + ".rdf", UTF_8));

		// Using a named graph to write the RDF file contents
		// To reference the default graph use GraphManager.DEFAULT_GRAPH.

		// Writing the first named graph
		graphManager.merge("example/recenzija/" + rdfFilePath + "/metadata", rdfFileHandle);

		// Release the client
		client2.release();

		System.out.println("[INFO] End.");
	}

}
