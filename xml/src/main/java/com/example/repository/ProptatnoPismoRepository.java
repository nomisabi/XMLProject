package com.example.repository;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.model.propratnopismo.ProptatnoPismo;
import com.example.model.propratnopismo.search.ProptatnoPismoSearchResult;

/**
 * Showcase for a simple repository allowing to access and modify
 * {@link Product} objects in a domain specific way.
 *
 * @author Niko Schmuck
 */
public interface ProptatnoPismoRepository {

	void add(ProptatnoPismo nr);

	void remove(String id);

	ProptatnoPismo findById(String id);

	ProptatnoPismoSearchResult findAll();

	Long count();

}
