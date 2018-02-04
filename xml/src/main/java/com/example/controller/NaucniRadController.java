package com.example.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.repository.ProductRepositoryXML;
import com.example.service.NaucniRadService;
import com.example.service.StorageService;

@RestController
public class NaucniRadController {

	private static final Logger logger = LoggerFactory.getLogger(NaucniRadController.class);

	@Autowired
	protected NaucniRadService naucniRadService;
	@Autowired
	StorageService storageService;
	
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

	/*@RequestMapping(value = "/naucni_radovi", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> createNaucniRad(@RequestBody String nr, UriComponentsBuilder builder) {
		try {
			naucniRadService.add(nr);
			//HttpHeaders headers = new HttpHeaders();
			// headers.setLocation(builder.path("/naucni_radovi/{id}.xml").buildAndExpand(nr.getId()).toUri());

			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (JAXBException | SAXException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>("Xml dokument nije validan", HttpStatus.BAD_REQUEST);
		}

	}
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

	@RequestMapping(value = "/naucni_radovi/odobreno", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> findByStatus() {
		String naucniRadovi;
		try {
			naucniRadovi = naucniRadService.findByStatus("Odobrodeno");
			return new ResponseEntity<>(naucniRadovi, HttpStatus.OK);
		} catch (IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
}
