package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.Revision;
import com.example.dto.SearchForm;
import com.example.dto.Work;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.Revizija;
import com.example.model.naucni_rad.TStatus;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.recenzija.Recenzija;
import com.example.model.recenzija.TPreporuka;
import com.example.model.recenzija.TStatusRecenzija;
import com.example.security.TokenUtils;

import com.example.service.NaucniRadService;

import com.example.service.StorageService;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

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
	public ResponseEntity<String> createNaucniRad(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		String message = "";
		try {
			storageService.deleteAll();
			storageService.init();
			storageService.store(file);

			String id = naucniRadService.add(file.getOriginalFilename(), username);
			storageService.deleteAll();

			if (id != null) {
				return ResponseEntity.status(HttpStatus.OK).body(id);
			} else {
				message = "Naucni rad " + file.getOriginalFilename() + " nije ispravan!";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			message = "Naucni rad " + file.getOriginalFilename() + " nije ispravan!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/api/naucni_radovi/{id}/revizija/{id_revizija}/propratno_pismo")
	public ResponseEntity<String> addPropratnoPismo(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision, @RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			storageService.deleteAll();
			storageService.init();
			storageService.store(file);

			naucniRadService.addLetter(id, idRevision, file.getOriginalFilename());
			storageService.deleteAll();

			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			logger.info(e.getMessage());
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/api/naucni_radovi/{id}/revizija/{id_revizija}/recenzija")
	public ResponseEntity<String> addRecenzija(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		String message = "";
		try {
			storageService.deleteAll();
			storageService.init();
			storageService.store(file);

			naucniRadService.addReviewUpload(id, idRevision, username, file.getOriginalFilename());
			storageService.deleteAll();

			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			logger.info(e.getMessage());
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/api/naucni_radovi/{id}/revizije")
	public ResponseEntity<String> createRevizija(@RequestParam("file") MultipartFile file, @PathVariable String id) {
		String message = "";
		try {
			storageService.deleteAll();
			storageService.init();
			storageService.store(file);

			String id1 = naucniRadService.addRevision(id, file.getOriginalFilename());
			storageService.deleteAll();

			if (id1 != null) {
				System.out.println(id1);
				return ResponseEntity.status(HttpStatus.OK).body(id1);
			} else {
				message = "Naucni rad " + file.getOriginalFilename() + " nije ispravan!";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			message = "Naucni rad " + file.getOriginalFilename() + " nije ispravan!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteNaucniRad(@PathVariable("id") String id, @PathVariable("id_revizija") String idRevision) {
		try {
			naucniRadService.remove(id, idRevision);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());

		}
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}", method = RequestMethod.GET)
	public ResponseEntity<Work> readNaucniRad(@PathVariable("id") String id) {

		try {
			Work work = naucniRadService.findByIdPoslat(id);
			return new ResponseEntity<>(work, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}", method = RequestMethod.GET)
	public ResponseEntity<Work> getNaucniRad(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision, HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		try {
			Work work = naucniRadService.findByIdForReview(id, idRevision, username);
			return new ResponseEntity<>(work, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/html", method = RequestMethod.GET)
	public ResponseEntity<Revizija> getRevizija(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision, HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		try {
			Revizija rev = naucniRadService.findReview(id, idRevision);
			return new ResponseEntity<>(rev, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
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
			List<Work> works = naucniRadService.findByStatus(TStatus.ODOBRODENO, "Odobrodeno");
			return new ResponseEntity<>(works, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/poslati", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Work>> findByStatusPoslato() {
		try {
			List<Work> works = naucniRadService.findByStatus(TStatus.POSLAT, "Poslat");
			return new ResponseEntity<>(works, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/u_proceduri", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Work>> findByStatusUproceduri() {
		try {
			List<Work> works = naucniRadService.findByStatus(TStatus.U_OBRADI, "U obradi");
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

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/recenzent", method = RequestMethod.POST)
	public ResponseEntity<Void> addReview(@PathVariable("id") String id, @PathVariable("id_revizija") String idRevision,
			@RequestBody Revision revision) {
		System.out.println(revision.getReview1());
		System.out.println(revision.getReview2());
		System.out.println(revision.getId());

		try {
			if (revision.getReview2() != null) {
				naucniRadService.addReview(id, idRevision, revision.getReview1(), revision.getReview2());
			} else {
				naucniRadService.addReview(id, idRevision, revision.getReview1());
			}
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (JAXBException | IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (MailException | InterruptedException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/dodeljeni", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Work>> getWorksForReviewer(HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		try {
			List<Work> works = naucniRadService.getWorksForReviewer(TStatusRecenzija.CEKA_SE, "Ceka se", username);
			if (works == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(works, HttpStatus.OK);
			}
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/prihvaceni", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Work>> getMyWorksForReviewer(HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		try {
			List<Work> works = naucniRadService.getWorksForReviewer(TStatusRecenzija.PRIHVACEN, "Prihvacen", username);
			if (works == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(works, HttpStatus.OK);
			}
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/recenzije", method = RequestMethod.GET)
	public ResponseEntity<Recenzija> getRecenzija(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision, HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		Recenzija recenzija;
		try {

			recenzija = naucniRadService.getReview(id, idRevision, username);
			if (recenzija == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			if (recenzija.getSadrzaj() != null) {
				return new ResponseEntity<>(recenzija, HttpStatus.OK);
			}
			Recenzija.Sadrzaj sadrzaj = new Recenzija.Sadrzaj();

			sadrzaj.setPreporuka(TPreporuka.POTREBNO_MANJAISPRAVKA);

			Recenzija.Sadrzaj.Pitanja pitanja = new Recenzija.Sadrzaj.Pitanja();
			pitanja.setTekstPitanja("Tema ovog rada je vredna istrage");
			pitanja.setOdgovor("Neopredeljen");
			sadrzaj.getPitanja().add(pitanja);

			pitanja = new Recenzija.Sadrzaj.Pitanja();
			pitanja.setTekstPitanja("U ovom radu prezentovane su nove informacije");
			pitanja.setOdgovor("Neopredeljen");
			sadrzaj.getPitanja().add(pitanja);

			pitanja = new Recenzija.Sadrzaj.Pitanja();
			pitanja.setTekstPitanja("Zakljucak ovog rada podrzan je odgovarajucim cinjenicama");
			pitanja.setOdgovor("Neopredeljen");
			sadrzaj.getPitanja().add(pitanja);

			recenzija.setSadrzaj(sadrzaj);

			return new ResponseEntity<>(recenzija, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/recenzije", method = RequestMethod.POST)
	public ResponseEntity<Void> addRecenzija(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision, HttpServletRequest request,
			@RequestBody Recenzija recenzija) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		System.out.println(recenzija.getStatus());
		System.out.println(recenzija.getSadrzaj().getPitanja().get(0).getTekstPitanja());
		System.out.println(recenzija.getSadrzaj().getPitanja().get(0).getOdgovor());
		System.out.println(recenzija.getSadrzaj().getPreporuka().toString());
		System.out.println(recenzija.getSadrzaj().getPitanja().get(3).getTekstPitanja());
		System.out.println(recenzija.getSadrzaj().getPitanja().get(4).getTekstPitanja());

		try {
			naucniRadService.addReview(recenzija, id, idRevision, username);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IOException | JAXBException | MailException | InterruptedException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/prihvati", method = RequestMethod.GET)
	public ResponseEntity<Void> yesReviwer(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision, HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		try {
			naucniRadService.acceptReview(id, idRevision, username, TStatusRecenzija.PRIHVACEN);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (JAXBException | IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/odbi", method = RequestMethod.GET)
	public ResponseEntity<Void> noReviwer(@PathVariable("id") String id, @PathVariable("id_revizija") String idRevision,
			HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		try {
			naucniRadService.acceptReview(id, idRevision, username, TStatusRecenzija.ODBIJEN);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (JAXBException | IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/objavi", method = RequestMethod.GET)
	public ResponseEntity<Void> publishRevision(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision) {

		try {
			naucniRadService.publishRevision(id, idRevision, TStatus.ODOBRODENO);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (JAXBException | IOException | MailException | InterruptedException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/potrebna_izmena", method = RequestMethod.GET)
	public ResponseEntity<Void> reviseRevision(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision) {

		try {
			naucniRadService.publishRevision(id, idRevision, TStatus.POTREBNA_IZMENA);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (JAXBException | IOException | MailException | InterruptedException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/revizija/{id_revizija}/odbaci", method = RequestMethod.GET)
	public ResponseEntity<Void> rejectRevision(@PathVariable("id") String id,
			@PathVariable("id_revizija") String idRevision) {

		try {
			naucniRadService.publishRevision(id, idRevision, TStatus.ODBIJEN);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (JAXBException | IOException | MailException | InterruptedException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/naucni_radovi", method = RequestMethod.GET, params = { "param" })
	public ResponseEntity<List<Work>> search(@RequestParam("param") String param) {

		List<Work> works = naucniRadService.search(param);
		return new ResponseEntity<>(works, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/naucni_radovi/search", method = RequestMethod.POST)
	public ResponseEntity<List<Work>> searchRdf(@RequestBody SearchForm form) throws IOException, JAXBException {

		System.out.println(form.toString());
		List<Work> works = naucniRadService.searchRdf(form);
		return new ResponseEntity<>(works, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/naucni_radovi/search/moji", method = RequestMethod.POST)
	public ResponseEntity<List<Work>> searchRdfMine(@RequestBody SearchForm form, HttpServletRequest request)
			throws IOException, JAXBException {

		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		System.out.println(form.toString());
		try {
			List<Work> works = naucniRadService.searchRdf(form, username);
			return new ResponseEntity<>(works, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (JAXBException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/api/naucni_radovi/moji", method = RequestMethod.GET, params = { "param" })
	public ResponseEntity<List<Work>> searchAuthor(@RequestParam("param") String param, HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		try {
			List<Work> works = naucniRadService.searchAuthor(username, param);
			return new ResponseEntity<>(works, HttpStatus.OK);
		} catch (IOException | JAXBException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * @RequestMapping(value = "/naucni_radovi/{id}.xml", method =
	 * RequestMethod.DELETE)
	 * 
	 * @ResponseStatus(HttpStatus.NO_CONTENT) public void
	 * deleteNaucniRad(@PathVariable("id") String id) {
	 * naucniRadService.remove(id); }
	 * 
	 *
	 * 
	 * @RequestMapping(value = "/naucni_radovi.xml", method = RequestMethod.GET,
	 * produces = MediaType.APPLICATION_XML_VALUE) public NaucniRadSearchResult
	 * searchNaucniRad(@RequestParam(required = false, value = "name") String
	 * name) { if (StringUtils.isEmpty(name)) {
	 * logger.info("Lookup all {} naucni rad...", naucniRadService.count());
	 * return naucniRadService.findAll(); } else {
	 * logger.info("Lookup products by name: {}", name); return null; } }
	 */

	@RequestMapping(value = "/api/naucni_radovi/{id}.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public NaucniRad getNaucniRad(@PathVariable("id") String id) throws IOException, JAXBException {
		return naucniRadService.findById(id);
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/download", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("id") String id)
			throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {

		File pdfFile = naucniRadService.createFile();
		InputStreamResource resource = naucniRadService.generatePDF(id, pdfFile);
		return ResponseEntity.ok().contentLength(pdfFile.length()).contentType(MediaType.APPLICATION_PDF)
				.body(resource);
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/html", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String downloadHTML(@PathVariable("id") String id)
			throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
		return naucniRadService.generateHTML(id);
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/no_author/html", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String downloadHTMLNoAuthor(@PathVariable("id") String id)
			throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
		return naucniRadService.generateHTMLWithoutAuthor(id);
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/no_author/download", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> downloadPDFFileNoAuthor(@PathVariable("id") String id)
			throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {

		File pdfFile = naucniRadService.createFile();
		InputStreamResource resource = naucniRadService.generatePDFWithoutAuthor(id, pdfFile);
		return ResponseEntity.ok().contentLength(pdfFile.length()).contentType(MediaType.APPLICATION_PDF)
				.body(resource);
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/rdf", method = RequestMethod.POST, consumes = "text/plain")
	public ResponseEntity<Void> addRdf(@PathVariable("id") String id, @RequestBody String rdf)
			throws JAXBException, SAXException, IOException, TransformerException {
		// System.out.println("1");
		naucniRadService.addRDF(id, rdf);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/rdf", method = RequestMethod.GET, produces = "text/plain")
	public ResponseEntity<String> getRDFText(@PathVariable("id") String id)
			throws JAXBException, SAXException, IOException, TransformerException {
		System.out.println("1");
		String s = naucniRadService.readRDF(id);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/json", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> getRDFJSON(@PathVariable("id") String id)
			throws JAXBException, SAXException, IOException, TransformerException {
		System.out.println("1");
		String s = naucniRadService.readRDFasJSON(id);

		return new ResponseEntity<>(s, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/naucni_radovi/{id}/links", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<String>> getLink(@PathVariable("id") String id)
			throws JAXBException, SAXException, IOException {
		ArrayList<String> s = naucniRadService.getLinks(id);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}

}
