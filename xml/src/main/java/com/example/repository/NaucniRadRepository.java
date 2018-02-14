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

	List<NaucniRad> findMy(String id) throws IOException, JAXBException;

	List<NaucniRad> findByReviewer(String status, String id) throws IOException, JAXBException;

	NaucniRad findByReviewerAndID(String status, String email, String id, String idRevision)
			throws IOException, JAXBException;

	List<NaucniRad> search(String param);

	List<NaucniRad> searchAuthor(String ime, String prezime, String email, String param);

	// ProductSearchResult findByName(String name);

	// ProductSearchResult findByYear(int year);

	Long count();

}
