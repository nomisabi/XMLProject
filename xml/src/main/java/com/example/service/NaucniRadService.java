package com.example.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.dto.Review;
import com.example.dto.Revision;
import com.example.dto.Work;
import com.example.korisnici.Korisnik;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.Revizija;
import com.example.model.naucni_rad.TStatus;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.propratnopismo.ProptatnoPismo;
import com.example.model.recenzija.Recenzija;
import com.example.model.recenzija.TStatusRecenzija;
import com.example.model.uloge.Recenzent;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.utils.NaucniRadUtils;
import com.example.utils.PropratnoPismoUtils;
import com.example.utils.Utils;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import net.sf.saxon.TransformerFactoryImpl;

@Service
public class NaucniRadService {

	@Autowired
	protected NaucniRadRepositoryXML nrRepositoryXML;
	@Autowired
	protected Korisnici2Service korisnici2Service;
	@Autowired
	EmailService emailService;
	@Autowired
	NaucniRadUtils naucniRadUtils;
	@Autowired
	PropratnoPismoUtils propratnoPismoUtils;
	@Autowired
	Utils utils;
	
	private static TransformerFactory transformerFactory;

	public static final String HTML_FILE = "gen/html/naucni_rad.html";

	public static final String XSL_FILE = "data/xsl/naucni_rad.xsl";

	public static final String XSL_FO_FILE = "data/xsl-fo/naucniRad-fo.xsl";

	public static final String OUTPUT_FILE = "gen/xsl/naucni_rad.pdf";

	public String add(String file) throws JAXBException, SAXException, IOException, TransformerException {
		NaucniRad nr = naucniRadUtils.unmarshalling(file);
		
		//metadata extractor
		InputStream in = new FileInputStream(new File("./upload-dir/" + file)); 
		OutputStream out = new FileOutputStream("gen/rdf/"+nr.getId()+".rdf");
		utils.extractMetadata(in, out);
		//write to database
		//Utils.writeRDFnr(Utils.loadProperties(), "gen/rdf/"+nr.getId()+".rdf");
		
		nr.setId(setIdNR());
		if (nr.getRevizija().size() == 1) {
			nr.getRevizija().get(0).setStatus(TStatus.POSLAT);
			nr.getRevizija().get(0).setId("RV1");

			String nrStr = naucniRadUtils.marshalling(nr);
			naucniRadUtils.validation(nrStr);

			nrRepositoryXML.add(nr);
			return nr.getId();
		} else {
			return null;
		}
	}

	public void remove(String id, String idRevision) throws IOException, JAXBException {
		NaucniRad naucniRad = nrRepositoryXML.findById(id);

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId() != null && revizija.getId().equals(idRevision)) {
				revizija.setStatus(TStatus.OBRISAN);
			}
		}
		nrRepositoryXML.add(naucniRad);
	}

	public NaucniRad findById(String id) throws IOException, JAXBException {
		return nrRepositoryXML.findById(id);
	}

	public Work findByIdForReview(String id, String idRevision, String username) throws IOException, JAXBException {
		NaucniRad naucniRad = nrRepositoryXML.findById(id);

		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		List<Revision> revisions = new ArrayList<>();
		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				Revision revision = new Revision(revizija.getId(), revizija.getNaslov(),
						revizija.getStatus().toString());
				for (Recenzija recenzija : revizija.getRecenzija()) {
					if (recenzija.getRecenzent().getEmail().equals(korisnik.getEmail())) {
						revision.setReview(new Review(recenzija.getId(), recenzija.getStatus().toString()));
					}
				}
				revisions.add(revision);
			}
		}

		return new Work(naucniRad.getId(), revisions);

	}
	
	public Revizija findReview(String id, String idRevision) throws IOException, JAXBException {
		NaucniRad naucniRad = nrRepositoryXML.findById(id);
		for (Revizija rev : naucniRad.getRevizija()) {
			System.out.println("rev: "+rev.getId());
			if (rev.getId().equals(idRevision))
				return rev;
		}
		return null;
	}

	public Recenzija getReview(String id, String idRevision, String username) throws IOException, JAXBException {
		NaucniRad naucniRad = nrRepositoryXML.findById(id);

		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				for (Recenzija recenzija : revizija.getRecenzija()) {
					if (recenzija.getRecenzent().getEmail().equals(korisnik.getEmail())) {
						return recenzija;

					}
				}

			}
		}

		return null;

	}

	public void addReview(Recenzija rec, String id, String idRevision, String username)
			throws IOException, JAXBException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		NaucniRad naucniRad = nrRepositoryXML.findByReviewerAndID("Prihvacen", korisnik.getEmail(), id, idRevision);

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				for (Recenzija recenzija : revizija.getRecenzija()) {
					if (recenzija.getRecenzent().getEmail().equals(korisnik.getEmail())
							&& recenzija.getStatus().equals(TStatusRecenzija.PRIHVACEN)) {
						Recenzija.Sadrzaj sadrzaj = new Recenzija.Sadrzaj();
						sadrzaj.getPitanja().add(rec.getSadrzaj().getPitanja().get(0));
						sadrzaj.getPitanja().add(rec.getSadrzaj().getPitanja().get(1));
						sadrzaj.getPitanja().add(rec.getSadrzaj().getPitanja().get(2));
						sadrzaj.setPreporuka(rec.getSadrzaj().getPreporuka());
						recenzija.setSadrzaj(sadrzaj);
					}
				}
			}

		}

		nrRepositoryXML.add(naucniRad);

	}

	public Work findByIdPoslat(String id) throws IOException, JAXBException {
		NaucniRad naucniRad = nrRepositoryXML.findById(id);
		List<Revision> revisions = new ArrayList<>();
		for (Revizija revizija : naucniRad.getRevizija()) {
			Revision revision = new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString());
			ProptatnoPismo pismo = revizija.getProptatnoPismo();
			if (pismo != null) {
				revision.setHasLetter(true);
			}
			revisions.add(revision);
		}

		return new Work(naucniRad.getId(), revisions);
	}

	public NaucniRadSearchResult findAll() {
		return nrRepositoryXML.findAll();
	}

	public List<Work> findByStatus(TStatus status, String statusStr) throws IOException, JAXBException {
		List<NaucniRad> radovi = nrRepositoryXML.findByStatus(statusStr);
		List<Work> works = new ArrayList<>();
		for (NaucniRad naucniRad : radovi) {
			List<Revision> revisions = new ArrayList<>();
			for (Revizija revizija : naucniRad.getRevizija()) {
				if (revizija.getStatus().equals(status)) {
					revisions
							.add(new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
				}
				works.add(new Work(naucniRad.getId(), revisions));
			}
		}
		return works;
	}

	public List<Work> findMy(String username) throws IOException, JAXBException {
		List<NaucniRad> radovi = nrRepositoryXML.findMy(username);
		List<Work> works = new ArrayList<>();

		for (NaucniRad naucniRad : radovi) {
			List<Revision> revisions = new ArrayList<>();
			for (Revizija revizija : naucniRad.getRevizija()) {
				if (revizija.getStatus().equals(TStatus.OBRISAN)) {
					continue;
				}
				revisions.add(new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
			}
			if (!revisions.isEmpty()) {
				works.add(new Work(naucniRad.getId(), revisions));
			}
		}
		return works;
	}

	public void addReview(String id, String idRevision, String username1, String username2)
			throws JAXBException, IOException, MailException, InterruptedException {
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
		emailService.sendMail(korisnik1.getEmail());

		Recenzija recenzija1 = new Recenzija();
		recenzija1.setRecenzent(recenzent1);
		recenzija1.setStatus(TStatusRecenzija.CEKA_SE);
		recenzija1.setId("RC1");

		Recenzija recenzija2 = new Recenzija();
		recenzija2.setRecenzent(recenzent2);
		recenzija2.setStatus(TStatusRecenzija.CEKA_SE);
		emailService.sendMail(korisnik2.getEmail());
		recenzent2.setId("RC2");

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				revizija.setStatus(TStatus.U_OBRADI);
				revizija.getRecenzija().add(recenzija1);
				revizija.getRecenzija().add(recenzija2);
				break;
			}
		}
		nrRepositoryXML.add(naucniRad);

	}

	public void addLetter(String id, String idRevision, String file) throws IOException, JAXBException, SAXException {
		NaucniRad naucniRad = findById(id);
		ProptatnoPismo pismo = propratnoPismoUtils.unmarshalling(file);

		// String pismoStr = propratnoPismoUtils.marshalling(pismo);
		// propratnoPismoUtils.validation(pismoStr);

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision) && revizija.getProptatnoPismo() == null) {
				pismo.setId("PP" + revizija.getId().substring(2, revizija.getId().length()));
				revizija.setProptatnoPismo(pismo);
				break;
			}
		}
		nrRepositoryXML.add(naucniRad);

	}

	public List<Work> getWorksForReviewer(TStatusRecenzija status, String statusStr, String username)
			throws IOException, JAXBException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		List<NaucniRad> radovi = nrRepositoryXML.findByReviewer(statusStr, korisnik.getIme(), korisnik.getPrezime(),
				korisnik.getEmail());
		List<Work> works = new ArrayList<>();
		for (NaucniRad naucniRad : radovi) {
			List<Revision> revisions = new ArrayList<>();
			for (Revizija revizija : naucniRad.getRevizija()) {
				Revision revision = new Revision(revizija.getId(), revizija.getNaslov(),
						revizija.getStatus().toString());
				for (Recenzija recenzija : revizija.getRecenzija()) {
					if (recenzija.getRecenzent().getEmail().equals(korisnik.getEmail())
							&& recenzija.getStatus().equals(status)) {
						revision.setReview(new Review(recenzija.getId(), recenzija.getStatus().toString()));
					}
				}
				if (revision.getReview() != null) {
					revisions.add(revision);
				}
			}
			if (!revisions.isEmpty()) {
				works.add(new Work(naucniRad.getId(), revisions));
			}
		}
		return works;

	}

	public void acceptReview(String id, String idRevision, String username, TStatusRecenzija status)
			throws JAXBException, IOException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);

		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		NaucniRad naucniRad = findById(id);

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				for (Recenzija recenzija : revizija.getRecenzija()) {
					if (recenzija.getRecenzent().getEmail().equals(korisnik.getEmail())
							&& recenzija.getRecenzent().getIme().equals(korisnik.getIme())) {
						recenzija.setStatus(status);
					}
				}
			}
		}
		nrRepositoryXML.add(naucniRad);
	}

	public Long count() {
		return nrRepositoryXML.count();
	}

	private String setIdNR() throws IOException, JAXBException {
		Random r = new Random();
		String id = "";
		int random = 0;
		while (true) {
			random = r.nextInt(1000000);
			id = "NR" + random;
			if (findById(id) == null) {
				return id;
			}
		}
	}

	public String generateHTML(String id)
			throws JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {
		NaucniRad nr = nrRepositoryXML.findById(id);
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
		Document doc = db.parse(new InputSource(new StringReader(xmlString)));
		DOMSource source = new DOMSource(doc);
		// DOMSource source = new DOMSource(buildDocument(xmlString));
		StreamResult result = new StreamResult(new FileOutputStream(HTML_FILE));
		transformer.transform(source, result);
		String content = Files.toString(new File(HTML_FILE), Charsets.UTF_8);
		return content;
	}

	public File createFile() {
		// Generate PDF file
		File pdfFile = new File(OUTPUT_FILE);
		if (!pdfFile.getParentFile().exists()) {
			System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
			pdfFile.getParentFile().mkdir();
		}
		return pdfFile;
	}

	public InputStreamResource generatePDF(String id, File pdfFile)
			throws JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {
		NaucniRad nr = nrRepositoryXML.findById(id);

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
