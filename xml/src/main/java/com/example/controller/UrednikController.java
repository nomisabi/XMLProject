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

import com.example.model.uloge.Urednik;
import com.example.service.UrednikService;


@RestController
public class UrednikController {

    private static final Logger logger = LoggerFactory.getLogger(UrednikController.class);
    
    @Autowired
    protected UrednikService urednikService;


    @RequestMapping(
            value = "/urednike",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> createUrednik(@RequestBody Urednik urednik, UriComponentsBuilder builder) {
        urednikService.add(urednik);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/urednike/{id}.xml")
                        .buildAndExpand(urednik.getId()).toUri());

        return new ResponseEntity<>("", headers, HttpStatus.CREATED);
    }

    
    
    @RequestMapping(
            value = "/urednike/{id}.xml",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrednik(@PathVariable("id") String id) {
        urednikService.remove(id);
    }

    @RequestMapping(
            value = "/urednike/{id}.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public Urednik readUrednik(@PathVariable("id") String id) {
        return urednikService.findById(id);
    }


}
