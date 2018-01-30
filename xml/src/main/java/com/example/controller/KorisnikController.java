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

import com.example.model.uloge.TKorisnik;
import com.example.service.KorisniciService;


@RestController
public class KorisnikController {

    private static final Logger logger = LoggerFactory.getLogger(KorisnikController.class);
    
    @Autowired
    protected KorisniciService korisnikService;


    @RequestMapping(
            value = "/korisnici",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> createTKorisnik(@RequestBody TKorisnik nr, UriComponentsBuilder builder) {
        korisnikService.add(nr);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/korisnik/{id}.xml")
                        .buildAndExpand(nr.getId()).toUri());

        return new ResponseEntity<>("", headers, HttpStatus.CREATED);
    }

    
    
    @RequestMapping(
            value = "/korisnik/{id}.xml",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTKorisnik(@PathVariable("id") String id) {
        korisnikService.remove(id);
    }

    @RequestMapping(
            value = "/korisnik/{id}.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public TKorisnik readTKorisnik(@PathVariable("id") String id) {
        return korisnikService.findById(id);
    }

    @RequestMapping(
            value = "/korisnik.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public com.example.model.uloge.search.TKorisnikSearchResult searchTKorisnik(@RequestParam(required=false, value="name") String name) {
        if (StringUtils.isEmpty(name)) {
            logger.info("Lookup all {} naucni rad...", korisnikService.count());
            return korisnikService.findAll();
        } else {
            logger.info("Lookup products by name: {}", name);
            return null;
        }
    }
}
