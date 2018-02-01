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

import com.example.model.uloge.Recenzent;
import com.example.model.uloge.search.RecenzentSearchResult;
import com.example.service.RecenzentService;


@RestController
public class RecenzentController {

    private static final Logger logger = LoggerFactory.getLogger(RecenzentController.class);
    
    @Autowired
    protected RecenzentService recenzentService;


    @RequestMapping(
            value = "/recenzenti",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> createRecenzent(@RequestBody Recenzent recenzent, UriComponentsBuilder builder) {
        recenzentService.add(recenzent);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/recenzenti/{id}.xml")
                        .buildAndExpand(recenzent.getId()).toUri());

        return new ResponseEntity<>("", headers, HttpStatus.CREATED);
    }

    
    
    @RequestMapping(
            value = "/recenzenti/{id}.xml",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecenzent(@PathVariable("id") String id) {
        recenzentService.remove(id);
    }

    @RequestMapping(
            value = "/recenzenti/{id}.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public Recenzent readRecenzent(@PathVariable("id") String id) {
        return recenzentService.findById(id);
    }

    @RequestMapping(
            value = "/recenzenti.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public RecenzentSearchResult searchRecenzent(@RequestParam(required=false, value="name") String name) {
        if (StringUtils.isEmpty(name)) {
            logger.info("Lookup all {} naucni rad...", recenzentService.count());
            return recenzentService.findAll();
        } else {
            logger.info("Lookup products by name: {}", name);
            return null;
        }
    }
}
