package com.example.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
import org.eclipse.rdf4j.rio.RDFFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.dto.Review;
import com.example.dto.Revision;
import com.example.dto.SearchForm;
import com.example.dto.Work;
import com.example.korisnici.Korisnik;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.Revizija;
import com.example.model.naucni_rad.TStatus;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.propratnopismo.ProptatnoPismo;
import com.example.model.recenzija.Recenzija;
import com.example.model.recenzija.TStatusRecenzija;
import com.example.model.uloge.Autor;
import com.example.model.uloge.Recenzent;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.utils.NaucniRadUtils;
import com.example.utils.PropratnoPismoUtils;
import com.example.utils.RdfJsonUtil;
import com.example.utils.RevizijaUtils;
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
	RevizijaUtils revizijaUtils;
	@Autowired
	Utils utils;

	private static TransformerFactory transformerFactory;

	public static final String HTML_FILE = "gen/html/naucni_rad.html";

	public static final String XSL_FILE = "data/xsl/naucni_rad.xsl";

	public static final String XSL_FO_FILE = "data/xsl-fo/naucniRad-fo.xsl";

	public static final String XSL_FILE_NO_AUTHOR = "data/xsl/naucni_rad_no_author.xsl";

	public static final String XSL_FO_FILE_NO_AUTHOR = "data/xsl-fo/naucniRad_fo_no_author.xsl";

	public static final String OUTPUT_FILE = "gen/xsl/naucni_rad.pdf";

	public String add(String file, String username)
			throws JAXBException, SAXException, IOException, TransformerException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		NaucniRad nr = naucniRadUtils.unmarshalling(file);
		String oldId = nr.getId();
		nr.setId(setIdNR());

		// metadata extractor
		InputStream in = new FileInputStream(new File("./upload-dir/" + file));
		String changed = Utils.getStringFromInputStream(in, oldId, nr.getId());
		InputStream changedIn = new ByteArrayInputStream(changed.getBytes(StandardCharsets.UTF_8));
		OutputStream out = new FileOutputStream("gen/rdf/" + nr.getId() + ".rdf");
		utils.extractMetadata(changedIn, out);

		boolean hasAuthor = false;

		if (nr.getRevizija().size() == 1) {
			nr.getRevizija().get(0).setStatus(TStatus.POSLAT);
			nr.getRevizija().get(0).setId("RV1");

			for (Autor autor : nr.getRevizija().get(0).getAutor()) {
				if (autor.getIme().equals(korisnik.getIme()) && autor.getPrezime().equals(korisnik.getPrezime())
						&& autor.getEmail().equals(korisnik.getEmail())) {
					autor.setId(korisnik.getId());
					hasAuthor = true;
					break;
				}
			}

			if (!hasAuthor) {
				System.out.print("Korisnik nije jedan od autora -> ne moze se dodati rad");
				return null;
			}

			// write to database
			NaucniRadUtils.writeRDFnr(nr.getId());

			String nrStr = naucniRadUtils.marshalling(nr);
			naucniRadUtils.validation(nrStr);

			nrRepositoryXML.add(nr);
			return nr.getId();
		} else {
			System.out.print("Rad ima vise od jedne revizije -> ne moze se dodati rad");
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
					if (recenzija.getRecenzent().getId().equals(korisnik.getId())) {
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
			System.out.println("rev: " + rev.getId());
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
					if (recenzija.getRecenzent().getId().equals(korisnik.getId())) {
						return recenzija;

					}
				}

			}
		}

		return null;

	}

	public void addReview(Recenzija rec, String id, String idRevision, String username)
			throws IOException, JAXBException, MailException, InterruptedException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		emailService.sendMailThanksReviewer(korisnik.getEmail());

		NaucniRad naucniRad = nrRepositoryXML.findByReviewerAndID("Prihvacen", korisnik.getId(), id, idRevision);

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				for (Recenzija recenzija : revizija.getRecenzija()) {
					if (recenzija.getRecenzent().getId().equals(korisnik.getId())
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
			if (revizija.getStatus().equals(TStatus.OBRISAN)) {
				continue;
			}
			Revision revision = new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString());
			ProptatnoPismo pismo = revizija.getProptatnoPismo();
			if (pismo != null) {
				revision.setHasLetter(true);
			}
			List<Recenzija> recenzije = revizija.getRecenzija();
			List<Review> reviews = new ArrayList<>();
			if (!recenzije.isEmpty()) {
				for (Recenzija recenzija : recenzije) {
					Review review = new Review(recenzija.getId(), recenzija.getStatus().toString());
					reviews.add(review);

				}
				revision.setReviews(reviews);
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
		Map<String, Work> worksHash = new HashMap<>();
		for (NaucniRad naucniRad : radovi) {
			List<Revision> revisions = new ArrayList<>();
			for (Revizija revizija : naucniRad.getRevizija()) {
				if (revizija.getStatus().equals(status)) {
					revisions
							.add(new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
				}
			}

			works.add(new Work(naucniRad.getId(), revisions));
		}
		return works;
	}

	public List<Work> findMy(String username) throws IOException, JAXBException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		List<NaucniRad> radovi = nrRepositoryXML.findMy(korisnik.getId());
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
		emailService.sendMailGetReview(korisnik1.getEmail(), id, idRevision);

		Recenzija recenzija1 = new Recenzija();
		recenzija1.setRecenzent(recenzent1);
		recenzija1.setStatus(TStatusRecenzija.CEKA_SE);
		recenzija1.setId("RC1");
		recenzent1.setId(korisnik1.getId());

		Recenzija recenzija2 = new Recenzija();
		recenzija2.setRecenzent(recenzent2);
		recenzija2.setStatus(TStatusRecenzija.CEKA_SE);
		emailService.sendMailGetReview(korisnik2.getEmail(), id, idRevision);
		recenzija2.setId("RC2");
		recenzent2.setId(korisnik2.getId());

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

	public void addReview(String id, String idRevision, String username1)
			throws JAXBException, IOException, MailException, InterruptedException {
		NaucniRad naucniRad = findById(id);
		String korisnikStr1 = korisnici2Service.pronadjiKorisnickoIme(username1);
		Korisnik korisnik1 = korisnici2Service.unmarshalling(korisnikStr1);
		Recenzent recenzent1 = new Recenzent();
		recenzent1.setEmail(korisnik1.getEmail());
		recenzent1.setIme(korisnik1.getIme());
		recenzent1.setPrezime(korisnik1.getPrezime());

		Recenzija recenzija1 = new Recenzija();
		recenzija1.setRecenzent(recenzent1);
		recenzija1.setStatus(TStatusRecenzija.CEKA_SE);
		recenzent1.setId(korisnik1.getId());

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				revizija.setStatus(TStatus.U_OBRADI);
				int idR = revizija.getRecenzija().size() + 1;
				recenzija1.setId("RC" + idR);
				revizija.getRecenzija().add(recenzija1);
				break;
			}
		}
		nrRepositoryXML.add(naucniRad);

	}

	public void addLetter(String id, String idRevision, String file)
			throws IOException, JAXBException, SAXException, TransformerException {
		NaucniRad naucniRad = findById(id);
		ProptatnoPismo pismo = propratnoPismoUtils.unmarshalling(file);

		// String pismoStr = propratnoPismoUtils.marshalling(pismo);
		// propratnoPismoUtils.validation(pismoStr);

		String newId = "";

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision) && revizija.getProptatnoPismo() == null) {
				pismo.setId("PP" + revizija.getId().substring(2, revizija.getId().length()));
				newId = pismo.getId();
				revizija.setProptatnoPismo(pismo);
				break;
			}
		}
		nrRepositoryXML.add(naucniRad);

		// rdf
		NaucniRad nrgen = nrRepositoryXML.findById(id);
		InputStream in = new FileInputStream(new File("./upload-dir/" + file));
		String changed = Utils.getStringFromInputStream(in, "", nrgen.getId(), "", idRevision, pismo.getId(), newId);
		InputStream changedIn = new ByteArrayInputStream(changed.getBytes(StandardCharsets.UTF_8));
		OutputStream out = new FileOutputStream("gen/rdf/" + id + ".rdf");
		utils.extractMetadata(changedIn, out);

		// write to database
		NaucniRadUtils.updateRDF(id);

	}

	public List<Work> getWorksForReviewer(TStatusRecenzija status, String statusStr, String username)
			throws IOException, JAXBException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		List<NaucniRad> radovi = nrRepositoryXML.findByReviewer(statusStr, korisnik.getId());
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

	public void publishRevision(String id, String idRevision, TStatus status)
			throws IOException, JAXBException, MailException, InterruptedException {
		NaucniRad naucniRad = findById(id);

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				revizija.setStatus(status);

				for (Autor autor : revizija.getAutor()) {
					emailService.sendMailRejectRevision(autor.getEmail(), revizija.getNaslov(), naucniRad.getId(),
							status.toString());
				}
			}
		}
		nrRepositoryXML.add(naucniRad);
	}

	public String addRevision(String id, String file) throws IOException, JAXBException, TransformerException {
		NaucniRad naucniRad = findById(id);
		Revizija revizija = revizijaUtils.unmarshalling(file);
		revizija.setStatus(TStatus.POSLAT);

		String oldId = revizija.getId();
		List<Revizija> revizije = naucniRad.getRevizija();
		int idR = revizije.size() + 1;
		revizija.setId("RV" + idR);
		naucniRad.getRevizija().add(revizija);

		nrRepositoryXML.add(naucniRad);

		// rdf
		InputStream in = new FileInputStream(new File("./upload-dir/" + file));
		String changed = Utils.getStringFromInputStream(in, id, revizija.getId(), oldId);
		InputStream changedIn = new ByteArrayInputStream(changed.getBytes(StandardCharsets.UTF_8));
		OutputStream out = new FileOutputStream("gen/rdf/" + id + ".rdf");
		utils.extractMetadata(changedIn, out);

		// write to database
		NaucniRadUtils.updateRDF(id);

		return "RV" + revizije.size() + 1;

	}

	public List<Work> search(String param) {
		List<NaucniRad> radovi = nrRepositoryXML.search(param);
		List<Work> works = new ArrayList<>();

		for (NaucniRad naucniRad : radovi) {
			List<Revision> revisions = new ArrayList<>();
			for (Revizija revizija : naucniRad.getRevizija()) {
				if (revizija.getStatus().equals(TStatus.ODOBRODENO)) {
					revisions
							.add(new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
				}
			}

			if (!revisions.isEmpty()) {
				works.add(new Work(naucniRad.getId(), revisions));
			}
		}
		return works;
	}

	public List<Work> searchAuthor(String username, String param) throws IOException, JAXBException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

		List<NaucniRad> radovi = nrRepositoryXML.searchAuthor(korisnik.getIme(), korisnik.getPrezime(),
				korisnik.getEmail(), param);
		List<Work> works = new ArrayList<>();

		for (NaucniRad naucniRad : radovi) {
			List<Revision> revisions = new ArrayList<>();
			for (Revizija revizija : naucniRad.getRevizija()) {
				for (Autor autor : revizija.getAutor()) {
					if (autor.getIme().equals(korisnik.getIme()) && autor.getPrezime().equals(korisnik.getPrezime())
							&& autor.getEmail().equals(korisnik.getEmail()))
						revisions.add(
								new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
				}
			}

			if (!revisions.isEmpty()) {
				works.add(new Work(naucniRad.getId(), revisions));
			}
		}
		return works;
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

	public String generateHTMLWithoutAuthor(String id)
			throws JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {
		NaucniRad nr = nrRepositoryXML.findById(id);
		TransformerFactory transformerFactory = new TransformerFactoryImpl();
		StreamSource transformSource = new StreamSource(new File(XSL_FILE_NO_AUTHOR));
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

	public InputStreamResource generatePDFWithoutAuthor(String id, File pdfFile)
			throws JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {
		NaucniRad nr = nrRepositoryXML.findById(id);

		// Initialize FOP factory object
		FopFactory fopFactory = FopFactory.newInstance(new File("src/main/java/fop.xconf"));

		// Setup the XSLT transformer factory
		TransformerFactory transformerFactory = new TransformerFactoryImpl();

		// Point to the XSL-FO file
		File xslFile = new File(XSL_FO_FILE_NO_AUTHOR);

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

	public void addRDF(String id, String file) throws JAXBException, SAXException, IOException, TransformerException {

		InputStream in = new ByteArrayInputStream(file.getBytes(StandardCharsets.UTF_8));
		OutputStream out = new FileOutputStream("gen/rdf/new.rdf");
		byte[] buffer = new byte[in.available()];
		in.read(buffer);
		out.write(buffer);

		out.close();
		in.close();

		// write to database
		NaucniRadUtils.updateRDFWithPath(id, "gen/rdf/new.rdf");

	}

	public String readRDF(String id) throws JAXBException, SAXException, IOException, TransformerException {
		return NaucniRadUtils.readRDF(id);
	}

	public String readRDFasJSON(String id) throws JAXBException, SAXException, IOException, TransformerException {
		NaucniRadUtils.readRDF(id);
		// InputStream in = new FileInputStream(new
		// File("gen/rdf/nr_metadata"+id+".rdf"));
		// try(InputStream in =
		// Thread.currentThread().getContextClassLoader().getResourceAsStream("gen/rdf/nr_metadata"+id+".rdf")){
		try (InputStream in = new FileInputStream(new File("gen/nr_metadata" + id + ".nt"))) {
			String retVal = RdfJsonUtil.getPrettyJsonLdString(in, RDFFormat.NTRIPLES);
			System.out.println(retVal);
			return retVal;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ArrayList<String> getLinks(String id) throws IOException {
		ArrayList<String> original = NaucniRadUtils.getLinks(id);
		ArrayList<String> updated = new ArrayList<String>();
		for (String string : original) {
			if (!updated.contains(string))
				if (!string.equals(id))
					updated.add(string);
		}

		return updated;
	}

	public List<Work> searchRdf(SearchForm form) throws IOException, JAXBException {
		ArrayList<String> list = NaucniRadUtils.search(form);
		List<Work> works= new ArrayList<Work>();
		for (String id:list) {
			NaucniRad nr = nrRepositoryXML.findById(id);
			List<Revision> rev = new ArrayList<Revision>();
			
			works.add(new Work(id, new ArrayList<>()));
		}	
		return works;
	}
	
	public List<Work> searchRdf(SearchForm form, String username) throws IOException, JAXBException {
		String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
		Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);
		form.setIme(korisnik.getIme());
		form.setPrezime(korisnik.getPrezime());		
		
		ArrayList<String> list = NaucniRadUtils.search(form);
		List<Work> works= new ArrayList<Work>();
		for (String id:list) {
			NaucniRad nr = nrRepositoryXML.findById(id);
			List<Revision> rev = new ArrayList<Revision>();
			
			works.add(new Work(id, new ArrayList<>()));
		}	
		return works;
	}

}
