package com.example.service;

import com.example.domain.NaucniRadSearchResult;
import com.example.domain.Product;
import com.example.domain.ProductSearchResult;
import com.example.model.naucni_rad.NaucniRad;

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
