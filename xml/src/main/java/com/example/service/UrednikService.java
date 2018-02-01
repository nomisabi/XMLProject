package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.uloge.Urednik;
import com.example.repository.UrednikRepositoryXML;

@Service
public class UrednikService {

	 @Autowired
	 protected UrednikRepositoryXML urednikRepositoryXML;
	 
	 public void add(Urednik urednik){
		 urednikRepositoryXML.add(urednik);
	 }

	 public void remove(String id){
		 urednikRepositoryXML.remove(id);
	 }

	 public Urednik findById(String id){
		return urednikRepositoryXML.findById(id);
	}

}
