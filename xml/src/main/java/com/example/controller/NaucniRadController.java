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
import org.w3c.dom.Document;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import com.example.dto.Work;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.repository.ProductRepositoryXML;
import com.example.security.TokenUtils;
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

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.InputSource;
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

  @RequestMapping(
          value = "/naucni_radovi",
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_XML_VALUE
  )
  public ResponseEntity<String> createNaucniRad(@RequestBody NaucniRad nr, UriComponentsBuilder builder) {
      naucniRadService.add(nr);

      HttpHeaders headers = new HttpHeaders();
      headers.setLocation(
              builder.path("/naucni_radovi/{id}.xml")
                      .buildAndExpand(nr.getId()).toUri());

      return new ResponseEntity<>("", headers, HttpStatus.CREATED);
  }

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
		}
	}
	

	
    @RequestMapping(value = "/api/naucni_radovi/{id}/download", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("id") String id)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
    	
    	File pdfFile=naucniRadService.createFile();
    	InputStreamResource resource = naucniRadService.generatePDF(id, pdfFile);
        return ResponseEntity
                .ok()
                .contentLength(pdfFile.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
    

    
    @RequestMapping(value = "/api/naucni_radovi/{id}/html", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String downloadHTML(@PathVariable("id") String id)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {  	  
        return naucniRadService.generateHTML(id);
    }	
	
}

