package com.example.repository;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;

/**
 * Showcase for a simple repository allowing to access and modify
 * {@link Product} objects in a domain specific way.
 *
 * @author Niko Schmuck
 */
public interface NaucniRadRepository {

	void add(NaucniRad nr);

	void remove(String id);

	NaucniRad findById(String id);

	NaucniRadSearchResult findAll();

	//ProductSearchResult findByName(String name);

	//ProductSearchResult findByYear(int year);

	Long count();

}
