package com.example.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.domain.NaucniRadSearchResult;
import com.example.domain.Product;
import com.example.domain.ProductSearchResult;
import com.example.model.naucni_rad.NaucniRad;
import com.example.service.NaucniRadRepositoryXML;
import com.example.service.ProductRepositoryXML;

@RestController
public class SampleRESTwithXMLController {

    private static final Logger logger = LoggerFactory.getLogger(SampleRESTwithXMLController.class);
    
    @Autowired
    protected NaucniRadRepositoryXML nrRepositoryXML;


    @RequestMapping(
            value = "/naucni_radovi",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> createNaucniRad(@RequestBody NaucniRad nr, UriComponentsBuilder builder) {
        nrRepositoryXML.add(nr);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/naucni_radovi/{id}.xml")
                        .buildAndExpand(nr.getId()).toUri());

        return new ResponseEntity<>("", headers, HttpStatus.CREATED);
    }

    
    
    @RequestMapping(
            value = "/naucni_radovi/{id}.xml",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNaucniRad(@PathVariable("id") String id) {
        nrRepositoryXML.remove(id);
    }

    @RequestMapping(
            value = "/naucni_radovi/{id}.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public NaucniRad readNaucniRad(@PathVariable("id") String id) {
        return nrRepositoryXML.findById(id);
    }

    @RequestMapping(
            value = "/naucni_rad.xml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public NaucniRadSearchResult searchNaucniRad(@RequestParam(required=false, value="name") String name) {
        if (StringUtils.isEmpty(name)) {
            logger.info("Lookup all {} naucni rad...", nrRepositoryXML.count());
            return nrRepositoryXML.findAll();
        } else {
            logger.info("Lookup products by name: {}", name);
            return null;
        }
    }
}
