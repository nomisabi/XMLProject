package com.example.controller;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.Work;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.repository.ProductRepositoryXML;
import com.example.security.TokenUtils;

import com.example.service.EmailService;
import com.example.service.NaucniRadService;

import com.example.service.StorageService;
import com.example.service.NaucniRadService;

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
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

@RestController
public class NaucniRadController {

	private static final Logger logger = LoggerFactory.getLogger(NaucniRadController.class);

	@Autowired
	protected NaucniRadService naucniRadService;
	@Autowired
	StorageService storageService;
	@Autowired
	TokenUtils tokenUtils;

	@PostMapping("/api/naucni_radovi")
	public ResponseEntity<String> createNaucniRad(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			storageService.deleteAll();
			storageService.init();
			storageService.store(file);

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			System.out.println(message);

			naucniRadService.add(file.getOriginalFilename());
			storageService.deleteAll();

			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	/*
	 * @RequestMapping(value = "/naucni_radovi", method = RequestMethod.POST,
	 * consumes = MediaType.APPLICATION_XML_VALUE) public ResponseEntity<String>
	 * createNaucniRad(@RequestBody String nr, UriComponentsBuilder builder) {
	 * try { naucniRadService.add(nr); //HttpHeaders headers = new
	 * HttpHeaders(); //
	 * headers.setLocation(builder.path("/naucni_radovi/{id}.xml").
	 * buildAndExpand(nr.getId()).toUri());
	 * 
	 * return new ResponseEntity<>(HttpStatus.CREATED); } catch (JAXBException |
	 * SAXException e) { logger.info(e.getMessage()); return new
	 * ResponseEntity<>("Xml dokument nije validan", HttpStatus.BAD_REQUEST); }
	 * 
	 * }
	 */

	@RequestMapping(value = "/naucni_radovi/{id}.xml", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteNaucniRad(@PathVariable("id") String id) {
		naucniRadService.remove(id);
	}

	@RequestMapping(value = "/naucni_radovi/{id}.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public NaucniRad readNaucniRad(@PathVariable("id") String id) {
		return naucniRadService.findById(id);
	}

	@RequestMapping(value = "/naucni_radovi.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public NaucniRadSearchResult searchNaucniRad(@RequestParam(required = false, value = "name") String name) {
		if (StringUtils.isEmpty(name)) {
			logger.info("Lookup all {} naucni rad...", naucniRadService.count());
			return naucniRadService.findAll();
		} else {
			logger.info("Lookup products by name: {}", name);
			return null;
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/odobreno", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Work>> findByStatusOdobreno() {
		try {
			List<Work> works = naucniRadService.findByStatus("Odobrodeno");
			return new ResponseEntity<>(works, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/poslati", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Work>> findByStatusPoslato() {
		try {
			List<Work> works = naucniRadService.findByStatus("Poslat");
			return new ResponseEntity<>(works, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/u_proceduri", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Work>> findByStatusUproceduri() {
		try {
			List<Work> works = naucniRadService.findByStatus("U obradi");
			return new ResponseEntity<>(works, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/moji", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Work>> findMy(HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		try {
			List<Work> works = naucniRadService.findMy(username);
			return new ResponseEntity<>(works, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/recenzent", method = RequestMethod.POST)
	public ResponseEntity<Void> addReview(@RequestBody Work work) {
		System.out.println(work.getReview1());
		System.out.println(work.getReview2());
		System.out.println(work.getId());

		try {
			naucniRadService.addReview(work.getId(), work.getReview1(), work.getReview2());
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (JAXBException | IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (MailException | InterruptedException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	public static final String XSL_FILE = "data/xsl/naucni_rad.xsl";

	public static final String OUTPUT_FILE = "gen/xsl/naucni_rad.pdf";

	@RequestMapping(value = "/naucni_radovi/{id}/download", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("id") String id)
			throws IOException, JAXBException, SAXException, TransformerException {

		NaucniRad nr = naucniRadService.findById(id);

		// Initialize FOP factory object
		FopFactory fopFactory = FopFactory.newInstance(new File("src/main/java/fop.xconf"));

		// Setup the XSLT transformer factory
		TransformerFactory transformerFactory = new TransformerFactoryImpl();

		// Point to the XSL-FO file
		File xslFile = new File(XSL_FILE);

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

		// Generate PDF file
		File pdfFile = new File(OUTPUT_FILE);
		if (!pdfFile.getParentFile().exists()) {
			System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
			pdfFile.getParentFile().mkdir();
		}

		OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
		out.write(outStream.toByteArray());

		System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
		out.close();

		System.out.println("[INFO] End.");
		InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));

		return ResponseEntity.ok().contentLength(pdfFile.length()).contentType(MediaType.APPLICATION_PDF)
				.body(resource);
	}

}
