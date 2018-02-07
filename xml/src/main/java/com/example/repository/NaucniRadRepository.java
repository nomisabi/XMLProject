package com.example.repository;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;

public interface NaucniRadRepository {

	void add(NaucniRad nr);

	void remove(String id);

	NaucniRad findById(String id) throws IOException, JAXBException;

	NaucniRadSearchResult findAll();

	List<NaucniRad> findByStatus(String status) throws IOException, JAXBException;

	List<NaucniRad> findMy(String username) throws IOException, JAXBException;

	List<NaucniRad> findByReviewer(String status, String firstName, String lastName, String email)
			throws IOException, JAXBException;

	// ProductSearchResult findByName(String name);

	// ProductSearchResult findByYear(int year);

	Long count();

}
