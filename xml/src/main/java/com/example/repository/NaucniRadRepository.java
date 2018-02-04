package com.example.repository;

import java.io.IOException;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;

public interface NaucniRadRepository {

	void add(NaucniRad nr);

	void remove(String id);

	NaucniRad findById(String id);

	NaucniRadSearchResult findAll();

	String findByStatus(String status) throws IOException;

	// ProductSearchResult findByName(String name);

	// ProductSearchResult findByYear(int year);

	Long count();

}
