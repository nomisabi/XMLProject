package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.propratnopismo.ProptatnoPismo;
import com.example.model.propratnopismo.search.ProptatnoPismoSearchResult;
import com.example.repository.ProptatnoPismoRepositoryXML;

@Service
public class ProtpatnoPismoService {

	 @Autowired
	 protected ProptatnoPismoRepositoryXML protpatnoPismoRepositoryXML;
	 
	 public void add(ProptatnoPismo pp){
		 protpatnoPismoRepositoryXML.add(pp);
	 }

	 public void remove(String id){
		 protpatnoPismoRepositoryXML.remove(id);
	 }

	 public ProptatnoPismo findById(String id){
		return protpatnoPismoRepositoryXML.findById(id);
	}

	 public ProptatnoPismoSearchResult findAll(){
		return protpatnoPismoRepositoryXML.findAll();
	}

	 public Long count(){
		return protpatnoPismoRepositoryXML.count();
	}

}
