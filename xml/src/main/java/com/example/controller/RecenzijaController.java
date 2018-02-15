package com.example.controller;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;

import com.example.model.recenzija.Recenzija;
import com.example.model.recenzija.search.RecenzijaSearchResult;
import com.example.service.RecenzijaService;


@RestController
public class RecenzijaController {

    private static final Logger logger = LoggerFactory.getLogger(RecenzijaController.class);
    
    @Autowired
    protected RecenzijaService recenzijaService;


    @RequestMapping(
            value = "/recenzije",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> createRecenzija(@RequestBody Recenzija recenzija, UriComponentsBuilder builder) {
        recenzijaService.add(recenzija);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/recenzije/{id}.xml")
                        .buildAndExpand(recenzija.getId()).toUri());

        return new ResponseEntity<>("", headers, HttpStatus.CREATED);
    }

    
    
    @RequestMapping(
            value = "/recenzije/{id}.xml",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecenzija(@PathVariable("id") String id) {
        recenzijaService.remove(id);
    }

    @RequestMapping(
            value = "/recenzije/{id}.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public Recenzija readRecenzija(@PathVariable("id") String id) {
        return recenzijaService.findById(id);
    }

    @RequestMapping(
            value = "/recenzije.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public RecenzijaSearchResult searchRecenzija(@RequestParam(required=false, value="name") String name) {
        if (StringUtils.isEmpty(name)) {
            logger.info("Lookup all {} naucni rad...", recenzijaService.count());
            return recenzijaService.findAll();
        } else {
            logger.info("Lookup products by name: {}", name);
            return null;
        }
    }
    
  /*  @RequestMapping(value = "api/recenzije/{id}/download", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("id") String id)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
    	
    	File pdfFile=recenzijaService.createFile();
    	InputStreamResource resource = recenzijaService.generatePDF(id, pdfFile);
        return ResponseEntity
                .ok()
                .contentLength(pdfFile.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }*/
    

    
    @RequestMapping(value = "api/recenzije/{id}/html", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String downloadHTML(@PathVariable("id") String id)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {  	  
        return recenzijaService.generateHTML(id);
    }
    
    @RequestMapping(value = "api/recenzije/{id}/no_creator/download", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFileNoCreator(@PathVariable("id") String id)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
    	
    	File pdfFile=recenzijaService.createFile();
    	InputStreamResource resource = recenzijaService.generatePDFNoRecenzent(id, pdfFile);
        return ResponseEntity
                .ok()
                .contentLength(pdfFile.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
    

    
    @RequestMapping(value = "api/recenzije/{id}/no_creator/html", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String downloadHTMLNoCreator(@PathVariable("id") String id)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {  	  
        return recenzijaService.generateHTMLNoRecenzent(id);
    }	
    
    @RequestMapping(value = "api/naunci_rad/{nrId}/revizija/{revId}/recenzije/download", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("nrId") String nrId, @PathVariable("revId") String revId)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
    	
    	File pdfFile=recenzijaService.createFile();
    	InputStreamResource resource = recenzijaService.generatePDF(nrId, revId, pdfFile);
        return ResponseEntity
                .ok()
                .contentLength(pdfFile.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
