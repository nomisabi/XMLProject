package com.example.repository;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.example.korisnici.Korisnici;

public interface Korisnik2Repository {

	void dodaj(String korisnik);

	String prondjiKorisnickoIme(String korisnickoIme) throws IOException;

	String pronadjiId(String id) throws IOException;

	String pronadjiSve() throws IOException;

	Korisnici pronadjiSveRecenzente() throws IOException, JAXBException;

	Korisnici pronadjiRecenzente(List<String> domeni) throws IOException, JAXBException;

	Korisnici pronadjiKorisnike(String param) throws IOException, JAXBException;
}