package com.example.repository;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.model.uloge.TKorisnik;
import com.example.model.uloge.search.TKorisnikSearchResult;

/**
 * Showcase for a simple repository allowing to access and modify
 * {@link Product} objects in a domain specific way.
 *
 * @author Niko Schmuck
 */
public interface KorisnikRepository {

	void add(TKorisnik nr);

	void remove(String id);

	TKorisnik findById(String id);

	TKorisnikSearchResult findAll();

	//ProductSearchResult findByName(String name);

	//ProductSearchResult findByYear(int year);

	Long count();

}
