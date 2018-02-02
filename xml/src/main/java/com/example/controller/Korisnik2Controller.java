package com.example.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.example.service.Korisnici2Service;

@RestController
public class Korisnik2Controller {
	private static final Logger logger = LoggerFactory.getLogger(Korisnik2Controller.class);

	@Autowired
	protected Korisnici2Service korisnici2Service;

	@RequestMapping(value = "/korisnici/novi", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> dodajKorisnika(@RequestBody String korisnik) {

		try {
			korisnici2Service.dodaj(korisnik);
			return new ResponseEntity<>("", HttpStatus.CREATED);
		} catch (JAXBException | SAXException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);

		}

	}

	@RequestMapping(value = "/korisnici", method = RequestMethod.GET, params = { "korisnicko_ime" })
	public ResponseEntity<String> pronadjiKorisnickoIme(@RequestParam("korisnicko_ime") String korisnickoIme) {
		try {
			String korisnik = korisnici2Service.pronadjiKorisnickoIme(korisnickoIme);
			if (korisnik != null) {
				return new ResponseEntity<>(korisnik, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/korisnici/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> pronadjiId(@PathVariable String id) {
		try {
			String korisnik = korisnici2Service.pronadjiId(id);
			if (korisnik != null) {
				return new ResponseEntity<>(korisnik, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/korisnici", method = RequestMethod.GET)
	public ResponseEntity<String> pronadjiSve() {
		try {
			String korisnici = korisnici2Service.pronadjiSve();
			if (korisnici != null) {
				return new ResponseEntity<>(korisnici, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
		}

	}

}
