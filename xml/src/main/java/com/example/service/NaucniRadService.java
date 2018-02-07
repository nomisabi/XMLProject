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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

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

import com.example.dto.Work;
import com.example.korisnici.Korisnik;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.Revizija;
import com.example.model.naucni_rad.TStatus;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.recenzija.Recenzija;
import com.example.model.uloge.Recenzent;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.utils.MyValidationEventHandler;
import com.example.utils.NSPrefixMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import net.sf.saxon.TransformerFactoryImpl;

@Service
public class NaucniRadService {

	@Autowired
	protected NaucniRadRepositoryXML nrRepositoryXML;
	@Autowired
	protected Korisnici2Service korisnici2Service;
	
    private static TransformerFactory transformerFactory;
	
	public static final String HTML_FILE = "gen/html/naucni_rad.html";
	
	public static final String XSL_FILE = "data/xsl/naucni_rad.xsl";
	
	public static final String XSL_FO_FILE = "data/xsl-fo/naucniRad-fo.xsl";
	
	public static final String OUTPUT_FILE = "gen/xsl/naucni_rad.pdf";

	public void add(String file) throws JAXBException, SAXException {
		NaucniRad nr = unmarshalling(file);
		nr.setId(setId());
		nr.getRevizija().get(0).setStatus(TStatus.POSLAT);
		// String nrStr = marshalling(nr);
		// validation(nrStr);
		nrRepositoryXML.add(nr);
	}
	
	 public void add(NaucniRad nr){
		 nrRepositoryXML.add(nr);
	 }

	public void remove(String id) {
		nrRepositoryXML.remove(id);
	}

	public NaucniRad findById(String id) {
		return nrRepositoryXML.findById(id);
	}

	public NaucniRadSearchResult findAll() {
		return nrRepositoryXML.findAll();
	}

	public List<Work> findByStatus(String status) throws IOException, JAXBException {
		List<NaucniRad> radovi = nrRepositoryXML.findByStatus(status);
		List<Work> works = new ArrayList<>();
		for (NaucniRad naucniRad : radovi) {
			for (Revizija revizija : naucniRad.getRevizija()) {
				// if (revizija.getStatus().equals(TStatus.ODOBRODENO)) {
				works.add(new Work(naucniRad.getId(), revizija.getNaslov()));
				// break;
				// }
			}
		}
		return works;
	}

	public List<Work> findMy(String username) throws IOException, JAXBException {
		List<NaucniRad> radovi = nrRepositoryXML.findMy(username);
		List<Work> works = new ArrayList<>();
		for (NaucniRad naucniRad : radovi) {
			for (Revizija revizija : naucniRad.getRevizija()) {
				works.add(new Work(naucniRad.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
			}
		}
		return works;
	}

	public void addReview(String id, String username1, String username2) throws JAXBException, IOException {
		NaucniRad naucniRad = findById(id);
		String korisnikStr1 = korisnici2Service.pronadjiKorisnickoIme(username1);
		Korisnik korisnik1 = korisnici2Service.unmarshalling(korisnikStr1);
		Recenzent recenzent1 = new Recenzent();
		recenzent1.setEmail(korisnik1.getEmail());
		recenzent1.setIme(korisnik1.getIme());
		recenzent1.setPrezime(korisnik1.getPrezime());

		String korisnikStr2 = korisnici2Service.pronadjiKorisnickoIme(username2);
		Korisnik korisnik2 = korisnici2Service.unmarshalling(korisnikStr2);
		Recenzent recenzent2 = new Recenzent();
		recenzent2.setEmail(korisnik2.getEmail());
		recenzent2.setIme(korisnik2.getIme());
		recenzent2.setPrezime(korisnik2.getPrezime());

		Recenzija recenzija1 = new Recenzija();
		recenzija1.setRecenzent(recenzent1);
		Recenzija recenzija2 = new Recenzija();
		recenzija2.setRecenzent(recenzent2);
		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getStatus().equals(TStatus.POSLAT)) {
				revizija.setStatus(TStatus.U_OBRADI);
				revizija.getRecenzija().add(recenzija1);
				revizija.getRecenzija().add(recenzija2);
				break;
			}
		}
		nrRepositoryXML.add(naucniRad);

	}

	public Long count() {
		return nrRepositoryXML.count();
	}

	private String setId() {
		Random r = new Random();
		String id = "";
		int random = 0;
		while (true) {
			random = r.nextInt(1000000);
			id = "ID" + random;
			if (findById(id) == null) {
				return id;
			}
		}
	}

	private JAXBContext getNaucniRadContext() throws JAXBException {
		return JAXBContext.newInstance(NaucniRad.class);
	}

	public NaucniRad unmarshalling(String file) throws JAXBException {
		Unmarshaller unmarshaller = this.getNaucniRadContext().createUnmarshaller();
		return (NaucniRad) unmarshaller.unmarshal(new File("./upload-dir/" + file));
	}

	private String marshalling(NaucniRad naucniRad) throws JAXBException {
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

	private void validation(String naucniRad) throws JAXBException, SAXException {
		Unmarshaller unmarshaller = this.getNaucniRadContext().createUnmarshaller();
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("data/xsd/naucni_rad.xsd"));

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		StringReader reader = new StringReader(naucniRad);
		unmarshaller.unmarshal(reader);
	}
	
	public String generateHTML(String id) throws JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException{
		NaucniRad nr= nrRepositoryXML.findById(id);
    	TransformerFactory transformerFactory = new TransformerFactoryImpl();
		StreamSource transformSource = new StreamSource(new File(XSL_FILE));
		Transformer transformer = transformerFactory.newTransformer(transformSource);
		transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		// Generate XHTML
		transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

    	JAXBContext jaxbContext = JAXBContext.newInstance(NaucniRad.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
    	StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(nr, sw);
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
		NaucniRad nr= nrRepositoryXML.findById(id);

    	// Initialize FOP factory object
    	FopFactory fopFactory = FopFactory.newInstance(new File("src/main/java/fop.xconf"));
    	
    	// Setup the XSLT transformer factory
    	TransformerFactory transformerFactory = new TransformerFactoryImpl();
		
		// Point to the XSL-FO file
		File xslFile = new File(XSL_FO_FILE);

		// Create transformation source
		StreamSource transformSource = new StreamSource(xslFile);		
		
		JAXBContext jaxbContext = JAXBContext.newInstance(NaucniRad.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(nr, sw);
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
