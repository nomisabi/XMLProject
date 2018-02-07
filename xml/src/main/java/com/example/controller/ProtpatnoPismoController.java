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

import com.example.model.propratnopismo.ProptatnoPismo;
import com.example.model.propratnopismo.search.ProptatnoPismoSearchResult;
import com.example.service.ProtpatnoPismoService;


@RestController
public class ProtpatnoPismoController {

    private static final Logger logger = LoggerFactory.getLogger(ProtpatnoPismoController.class);
    
    @Autowired
    protected ProtpatnoPismoService protpatnoPismoService;


    @RequestMapping(
            value = "/protpatnoPisma",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> createProtpatnoPismo(@RequestBody ProptatnoPismo nr, UriComponentsBuilder builder) {
        protpatnoPismoService.add(nr);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/protpatnoPisma/{id}.xml")
                        .buildAndExpand(nr.getId()).toUri());

        return new ResponseEntity<>("", headers, HttpStatus.CREATED);
    }

    
    
    @RequestMapping(
            value = "/protpatnoPisma/{id}.xml",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProtpatnoPismo(@PathVariable("id") String id) {
        protpatnoPismoService.remove(id);
    }

    @RequestMapping(
            value = "/protpatnoPisma/{id}.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ProptatnoPismo readProtpatnoPismo(@PathVariable("id") String id) {
        return protpatnoPismoService.findById(id);
    }

    @RequestMapping(
            value = "/protpatnoPisma.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ProptatnoPismoSearchResult searchProtpatnoPismo(@RequestParam(required=false, value="name") String name) {
        if (StringUtils.isEmpty(name)) {
            logger.info("Lookup all {} naucni rad...", protpatnoPismoService.count());
            return protpatnoPismoService.findAll();
        } else {
            logger.info("Lookup products by name: {}", name);
            return null;
        }
    }
    
    @RequestMapping(value = "api/protpatnoPisma/{id}/download", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile(@PathVariable("id") String id)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
    	
    	File pdfFile=protpatnoPismoService.createFile();
    	InputStreamResource resource = protpatnoPismoService.generatePDF(id, pdfFile);
        return ResponseEntity
                .ok()
                .contentLength(pdfFile.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
    

    
    @RequestMapping(value = "api/protpatnoPisma/{id}/html", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String downloadHTML(@PathVariable("id") String id)
            throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {  	  
        return protpatnoPismoService.generateHTML(id);
    }	
}
