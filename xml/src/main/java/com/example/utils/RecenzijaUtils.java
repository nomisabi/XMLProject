package com.example.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.io.FileHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;

public class RecenzijaUtils {
	
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
		graphManager.merge("example/recenzija/"+rdfFilePath+"/metadata", rdfFileHandle);
		
		
		// Release the client
		client2.release();
		
		System.out.println("[INFO] End.");
	}
	
}
