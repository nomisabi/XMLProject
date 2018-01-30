package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.repository.NaucniRadRepositoryXML;

@Service
public class NaucniRadService {

	 @Autowired
	 protected NaucniRadRepositoryXML nrRepositoryXML;
	 
	 public void add(NaucniRad nr){
		 nrRepositoryXML.add(nr);
	 }

	 public void remove(String id){
		 nrRepositoryXML.remove(id);
	 }

	 public NaucniRad findById(String id){
		return nrRepositoryXML.findById(id);
	}

	 public NaucniRadSearchResult findAll(){
		return nrRepositoryXML.findAll();
	}

	 public Long count(){
		return nrRepositoryXML.count();
	}

}
