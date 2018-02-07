package com.example.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.model.recenzija.Recenzija;
import com.example.model.recenzija.search.RecenzijaSearchResult;
import com.example.repository.RecenzijaRepositoryXML;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import net.sf.saxon.TransformerFactoryImpl;

@Service
public class RecenzijaService {

	 @Autowired
	 protected RecenzijaRepositoryXML recenzijaRepositoryXML;
	 
	 public static final String HTML_FILE = "gen/html/recenzija.html";
		
	 public static final String XSL_FILE = "data/xsl/recenzija.xsl";
	 
	 public static final String XSL_FO_FILE = "data/xsl-fo/recenzija_fo.xsl";
		
	 public static final String OUTPUT_FILE = "gen/xsl/recenzija.pdf";
	 
	 public void add(Recenzija recenzija){
		 recenzijaRepositoryXML.add(recenzija);
	 }

	 public void remove(String id){
		 recenzijaRepositoryXML.remove(id);
	 }

	 public Recenzija findById(String id){
		return recenzijaRepositoryXML.findById(id);
	}

	 public RecenzijaSearchResult findAll(){
		return recenzijaRepositoryXML.findAll();
	}

	 public Long count(){
		return recenzijaRepositoryXML.count();
	}

		public String generateHTML(String id) throws JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException{
			Recenzija rec= recenzijaRepositoryXML.findById(id);
	    	TransformerFactory transformerFactory = new TransformerFactoryImpl();
			StreamSource transformSource = new StreamSource(new File(XSL_FILE));
			Transformer transformer = transformerFactory.newTransformer(transformSource);
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			// Generate XHTML
			transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

	    	JAXBContext jaxbContext = JAXBContext.newInstance(Recenzija.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
	    	StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(rec, sw);
			String xmlString = sw.toString();
			
			// Initialize the transformation subject
			StreamSource xmlSource = new StreamSource(new StringReader(xmlString));
			
			// Transform DOM to HTML
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse( new InputSource( new StringReader( xmlString ) ) ); 
			DOMSource source = new DOMSource(doc);
			//DOMSource source = new DOMSource(buildDocument(xmlString));
			StreamResult result = new StreamResult(new FileOutputStream(HTML_FILE));
			transformer.transform(source, result);
			String content = Files.toString(new File(HTML_FILE), Charsets.UTF_8);
			return content;
		}
		
		public File createFile(){
			// Generate PDF file
			File pdfFile = new File(OUTPUT_FILE);
					if (!pdfFile.getParentFile().exists()) {
						System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
						pdfFile.getParentFile().mkdir();
					}
			return pdfFile;
		}
		
		public InputStreamResource generatePDF(String id, File pdfFile) throws JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException{
			Recenzija rec= recenzijaRepositoryXML.findById(id);

	    	// Initialize FOP factory object
	    	FopFactory fopFactory = FopFactory.newInstance(new File("src/main/java/fop.xconf"));
	    	
	    	// Setup the XSLT transformer factory
	    	TransformerFactory transformerFactory = new TransformerFactoryImpl();
			
			// Point to the XSL-FO file
			File xslFile = new File(XSL_FO_FILE);

			// Create transformation source
			StreamSource transformSource = new StreamSource(xslFile);		
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Recenzija.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(rec, sw);
			String xmlString = sw.toString();
			
			// Initialize the transformation subject
			StreamSource source = new StreamSource(new StringReader(xmlString));

			// Initialize user agent needed for the transformation
			FOUserAgent userAgent = fopFactory.newFOUserAgent();
			
			// Create the output stream to store the results
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			// Initialize the XSL-FO transformer object
			Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
			
			// Construct FOP instance with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

			// Resulting SAX events 
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			xslFoTransformer.transform(source, res);

			
			
			OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
			out.write(outStream.toByteArray());

			System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
			out.close();
			
			System.out.println("[INFO] End.");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));
			return resource;
	    	
		}
}
