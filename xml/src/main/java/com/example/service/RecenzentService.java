package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.uloge.Recenzent;
import com.example.model.uloge.search.RecenzentSearchResult;
import com.example.repository.RecenzentRepositoryXML;

@Service
public class RecenzentService {

	 @Autowired
	 protected RecenzentRepositoryXML recenzentRepositoryXML;
	 
	 public void add(Recenzent recenzent){
		 recenzentRepositoryXML.add(recenzent);
	 }

	 public void remove(String id){
		 recenzentRepositoryXML.remove(id);
	 }

	 public Recenzent findById(String id){
		return recenzentRepositoryXML.findById(id);
	}

	 public RecenzentSearchResult findAll(){
		return recenzentRepositoryXML.findAll();
	}

	 public Long count(){
		return recenzentRepositoryXML.count();
	}

}
