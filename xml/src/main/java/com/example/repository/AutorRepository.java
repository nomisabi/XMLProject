package com.example.repository;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.model.uloge.Autor;
import com.example.model.uloge.TKorisnik;
import com.example.model.uloge.search.AutorSearchResult;

/**
 * Showcase for a simple repository allowing to access and modify
 * {@link Product} objects in a domain specific way.
 *
 * @author Niko Schmuck
 */
public interface AutorRepository {

	void add(Autor nr);

	void remove(String id);

	Autor findById(String id);

	AutorSearchResult findAll();

	TKorisnik findByUsername(String username);

	// ProductSearchResult findByName(String name);

	// ProductSearchResult findByYear(int year);

	Long count();

}
