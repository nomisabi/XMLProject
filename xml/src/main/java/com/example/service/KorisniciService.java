package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.uloge.TKorisnik;
import com.example.model.uloge.search.TKorisnikSearchResult;
import com.example.repository.KorisnikRepositoryXML;

@Service
public class KorisniciService {

	 @Autowired
	 protected KorisnikRepositoryXML korisnikRepositoryXML;
	 
	 public void add(TKorisnik nr){
		 korisnikRepositoryXML.add(nr);
	 }

	 public void remove(String id){
		 korisnikRepositoryXML.remove(id);
	 }

	 public TKorisnik findById(String id){
		return korisnikRepositoryXML.findById(id);
	}

	 public TKorisnikSearchResult findAll(){
		return korisnikRepositoryXML.findAll();
	}

	 public Long count(){
		return korisnikRepositoryXML.count();
	}

}
