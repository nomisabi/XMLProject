package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
