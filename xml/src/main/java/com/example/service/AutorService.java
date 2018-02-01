package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.uloge.Autor;
import com.example.model.uloge.search.AutorSearchResult;
import com.example.repository.AutorRepositoryXML;

@Service
public class AutorService {

	 @Autowired
	 protected AutorRepositoryXML autorRepositoryXML;
	 
	 public void add(Autor nr){
		 autorRepositoryXML.add(nr);
	 }

	 public void remove(String id){
		 autorRepositoryXML.remove(id);
	 }

	 public Autor findById(String id){
		return autorRepositoryXML.findById(id);
	}

	 public AutorSearchResult findAll(){
		return autorRepositoryXML.findAll();
	}

	 public Long count(){
		return autorRepositoryXML.count();
	}

}
