package com.example.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.example.model.naucni_rad.NaucniRad;
import com.fasterxml.jackson.databind.JsonNode;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.FailedRequestException;
import com.marklogic.client.ResourceNotFoundException;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.FileHandle;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.marklogic.client.semantics.SPARQLMimeTypes;
import com.marklogic.client.semantics.SPARQLQueryDefinition;
import com.marklogic.client.semantics.SPARQLQueryManager;


@Component
public class NaucniRadUtils {
	

	private static final String NR_GRAPH_URI = "example/naucni_rad/metadata";
	
	private JAXBContext getNaucniRadContext() throws JAXBException {
		return JAXBContext.newInstance(NaucniRad.class);
	}

	public NaucniRad unmarshalling(String file) throws JAXBException {
		Unmarshaller unmarshaller = this.getNaucniRadContext().createUnmarshaller();
		return (NaucniRad) unmarshaller.unmarshal(new File("./upload-dir/" + file));
	}

	public String marshalling(NaucniRad naucniRad) throws JAXBException {
		Marshaller marshaller = this.getNaucniRadContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/naucni_rad", "nr"));
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/uloge", "ulog"));
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(naucniRad, sw);
		return sw.toString();
	}

	public void validation(String naucniRad) throws JAXBException, SAXException {
		Unmarshaller unmarshaller = this.getNaucniRadContext().createUnmarshaller();
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("data/xsd/naucni_rad.xsd"));

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		StringReader reader = new StringReader(naucniRad);
		unmarshaller.unmarshal(reader);
	}

	public static void writeRDFnr( String rdfFilePath) throws IOException {
		
		DatabaseClient client2= DatabaseClientFactory.newClient("localhost", 8000, "admin", "admin",
				DatabaseClientFactory.Authentication.DIGEST);
		
		// Create a document manager to work with XML files.
		GraphManager graphManager = client2.newGraphManager();
		
		// Set the default media type (RDF/XML)
		graphManager.setDefaultMimetype(RDFMimeTypes.RDFXML);
		

		// A handle to hold the RDF content.
		FileHandle rdfFileHandle =
				new FileHandle(new File("gen/rdf/"+rdfFilePath+".rdf"))
				.withMimetype(RDFMimeTypes.RDFXML);
		
		// Write the document to the database
		System.out.println("[INFO] Loading triples from \"" + rdfFilePath + "\"\n\n" + FileUtils.readFile("gen/rdf/"+rdfFilePath+".rdf", UTF_8));
		
		// Using a named graph to write the RDF file contents
		// To reference the default graph use GraphManager.DEFAULT_GRAPH.
		
		// Writing the first named graph
		graphManager.write(NR_GRAPH_URI+"/"+rdfFilePath, rdfFileHandle);
		
		System.out.println("itt is");
		
		
		// Release the client
		client2.release();
		
		System.out.println("[INFO] End.");
	}
	public static void updateRDF( String rdfFilePath) throws IOException {
		
		DatabaseClient client2= DatabaseClientFactory.newClient("localhost", 8000, "admin", "admin",
				DatabaseClientFactory.Authentication.DIGEST);
		
		// Create a document manager to work with XML files.
		GraphManager graphManager = client2.newGraphManager();
		
		// Set the default media type (RDF/XML)
		graphManager.setDefaultMimetype(RDFMimeTypes.RDFXML);
		
		try{
			if (readRDF(rdfFilePath) !=null){
			FileHandle rdfFileHandle1 =
					new FileHandle(new File("gen/rdf/nr_metadata.rdf"))
					.withMimetype(RDFMimeTypes.RDFXML);
			
			// Write the document to the database
			System.out.println("[INFO] Loading triples from \"" + rdfFilePath + "\"\n\n" + FileUtils.readFile(rdfFilePath, UTF_8));
			
	
			// Writing the first named graph
			graphManager.merge(NR_GRAPH_URI+"/"+rdfFilePath, rdfFileHandle1);
			}
		} catch (ResourceNotFoundException e){
			
		}
		// A handle to hold the RDF content.
		FileHandle rdfFileHandle =
				new FileHandle(new File("gen/rdf/"+rdfFilePath+".rdf"))
				.withMimetype(RDFMimeTypes.RDFXML);
		
		// Write the document to the database
		System.out.println("[INFO] Loading triples from \"" + rdfFilePath + "\"\n\n" + FileUtils.readFile("gen/rdf/"+rdfFilePath+".rdf", UTF_8));
		
		// Using a named graph to write the RDF file contents
		// To reference the default graph use GraphManager.DEFAULT_GRAPH.
		
		try{
			// Writing the first named graph
			graphManager.merge(NR_GRAPH_URI+"/"+rdfFilePath, rdfFileHandle);
		} catch(FailedRequestException e){
			graphManager.write(NR_GRAPH_URI+"/"+rdfFilePath, rdfFileHandle);
		}
		// Writing the first named graph
		
		
		System.out.println("itt is");
		// Release the client
		client2.release();
		
		System.out.println("[INFO] End.");
	}
	

	public static void updateRDFWithPath(String id, String rdfFilePath) throws IOException {
		
		DatabaseClient client2= DatabaseClientFactory.newClient("localhost", 8000, "admin", "admin",
				DatabaseClientFactory.Authentication.DIGEST);
		
		// Create a document manager to work with XML files.
		GraphManager graphManager = client2.newGraphManager();
		
		// Set the default media type (RDF/XML)
		graphManager.setDefaultMimetype(RDFMimeTypes.RDFXML);
		try{
			if (readRDF(id) !=null){
			FileHandle rdfFileHandle1 =
					new FileHandle(new File("gen/rdf/nr_metadata"+id+".rdf"))
					.withMimetype(RDFMimeTypes.RDFXML);
			
			// Write the document to the database
			System.out.println("[INFO] Loading triples from gen/rdf/nr_metadata.rdf\"\n\n" + FileUtils.readFile(rdfFilePath, UTF_8));
			
	
			// Writing the first named graph
			graphManager.merge(NR_GRAPH_URI+"/"+id, rdfFileHandle1);}
		} catch (ResourceNotFoundException e){
					
		}
		// A handle to hold the RDF content.
		FileHandle rdfFileHandle =
				new FileHandle(new File(rdfFilePath))
				.withMimetype(RDFMimeTypes.RDFXML);
		
		// Write the document to the database
		System.out.println("[INFO] Loading triples from \"" + rdfFilePath + "\"\n\n" + FileUtils.readFile(rdfFilePath, UTF_8));
		
		try{
		// Writing the first named graph
			graphManager.merge(NR_GRAPH_URI+"/"+id, rdfFileHandle);
		} catch(FailedRequestException e){
			graphManager.write(NR_GRAPH_URI+"/"+id, rdfFileHandle);
		}
		
		// Release the client
		client2.release();
		
		System.out.println("[INFO] End.");
	}
	
	public static String readRDF(String id) throws IOException {
		
			DatabaseClient client2= DatabaseClientFactory.newClient("localhost", 8000, "admin", "admin",
					DatabaseClientFactory.Authentication.DIGEST);
	
			// Create a document manager to work with XML files.
			GraphManager graphManager = client2.newGraphManager();
			
			// Set the N-triples as the default media type (application/n-triples)
			graphManager.setDefaultMimetype(RDFMimeTypes.NTRIPLES);
			
			// Define a DOM handle instance to hold the results 
			DOMHandle domHandle = new DOMHandle();
			
			try{
			// Retrieve RDF triplets in format (RDF/XML) other than default
			graphManager.read(NR_GRAPH_URI+'/'+id, domHandle).withMimetype(RDFMimeTypes.RDFXML);
			
			// Serialize document to the standard output stream
			//System.out.println("[INFO] Rendering triples as \"application/rdf+xml\".");
			DOMUtil.transform(domHandle.get(), System.out);
	
			// Write the results to an RDF file
			String filePath = "gen/rdf/nr_metadata"+id+".rdf";
		//	System.out.println("[INFO] Writing triples to the file \"" + filePath + "\".");
			DOMUtil.transform(domHandle.get(), new FileOutputStream(filePath));
	
			
			// Define a String handle instance to hold the results 
			StringHandle stringHandle = new StringHandle();
			
			// Retrieve triples in the default format
			graphManager.read(NR_GRAPH_URI+'/'+id, stringHandle);
	
			// Display the results to the standard output
			//System.out.println("[INFO] Rendering triples as \"application/n-triples\".");
			//System.out.println(stringHandle.get());
			
			// Write the results to an .nt file
			filePath = "gen/nr_metadata"+id+".nt";
			
			//System.out.println("[INFO] Writing triples to the file \"" + filePath + "\".");
			FileUtils.writeFile(filePath, stringHandle.get());
		
			
			// Release the client
			client2.release();
			
			//System.out.println();
			//System.out.println("[INFO] End.");
			return stringHandle.get();
		} catch(ResourceNotFoundException e){
			client2.release();
			return null;
		}
	}
	
	public static ArrayList<String> getLinks(String id) throws IOException {
		
		DatabaseClient client2= DatabaseClientFactory.newClient("localhost", 8000, "admin", "admin",
				DatabaseClientFactory.Authentication.DIGEST);
		
		// Create a SPARQL query manager to query RDF datasets
		SPARQLQueryManager sparqlQueryManager = client2.newSPARQLQueryManager();
						
		// Initialize Jackson results handle
		JacksonHandle resultsHandle = new JacksonHandle();
		resultsHandle.setMimetype(SPARQLMimeTypes.SPARQL_JSON);

		
		// Initialize SPARQL query definition - retrieves all triples from RDF dataset 
		SPARQLQueryDefinition query = sparqlQueryManager.newQueryDefinition("SELECT * WHERE { ?s ?p ?o .FILTER regex(str(?o), ?i) . FILTER regex(str(?p), \"referencaNaucniRad\") .}").withBinding("i", id);

		System.out.println("[INFO] Showing all of the triples from RDF dataset using result handler\n");
		resultsHandle = sparqlQueryManager.executeSelect(query, resultsHandle);
		handleResults(resultsHandle);
		
		ArrayList<String> works= new ArrayList<String>();
		JsonNode tuples = resultsHandle.get().path("results").path("bindings");
		for ( JsonNode row : tuples ) {
			String subject = row.path("s").path("value").asText();
			String predicate = row.path("p").path("value").asText();
			String object = row.path("o").path("value").asText();
			
			works.add(getNaucniRad(subject));
			
			if (!subject.equals("")) System.out.println(subject);
			System.out.println("\t" + predicate + " \n\t" + object + "\n");
		}

		client2.release();
		
		System.out.println("[INFO] End.");
		return works;
	}
	
	private static String getNaucniRad(String path){
		String[] first= path.split("/naucni_rad/");
		String[] second= first[1].split("/revizija/");
		return second[0];
	}
	
	private static void handleResults(JacksonHandle resultsHandle) {
		JsonNode tuples = resultsHandle.get().path("results").path("bindings");
		for ( JsonNode row : tuples ) {
			String subject = row.path("s").path("value").asText();
			String predicate = row.path("p").path("value").asText();
			String object = row.path("o").path("value").asText();
			
			if (!subject.equals("")) System.out.println(subject);
			System.out.println("\t" + predicate + " \n\t" + object + "\n");
		}
	}
	
	
}
