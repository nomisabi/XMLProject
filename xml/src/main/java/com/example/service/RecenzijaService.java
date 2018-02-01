package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.recenzija.Recenzija;
import com.example.model.recenzija.search.RecenzijaSearchResult;
import com.example.repository.RecenzijaRepositoryXML;

@Service
public class RecenzijaService {

	 @Autowired
	 protected RecenzijaRepositoryXML recenzijaRepositoryXML;
	 
	 public void add(Recenzija recenzija){
		 recenzijaRepositoryXML.add(recenzija);
	 }

	 public void remove(String id){
		 recenzijaRepositoryXML.remove(id);
	 }

	 public Recenzija findById(String id){
		return recenzijaRepositoryXML.findById(id);
	}

	 public RecenzijaSearchResult findAll(){
		return recenzijaRepositoryXML.findAll();
	}

	 public Long count(){
		return recenzijaRepositoryXML.count();
	}

}
