package com.example.repository;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.example.korisnici.Korisnici;

public interface Korisnik2Repository {

	void dodaj(String korisnik);

	String prondjiKorisnickoIme(String korisnickoIme) throws IOException;

	String pronadjiId(String id) throws IOException;

	String pronadjiSve() throws IOException;

	Korisnici pronadjiSveRecenzente() throws IOException, JAXBException;

}
