package com.example.repository;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.model.uloge.Recenzent;
import com.example.model.uloge.TKorisnik;
import com.example.model.uloge.search.RecenzentSearchResult;

/**
 * Showcase for a simple repository allowing to access and modify
 * {@link Product} objects in a domain specific way.
 *
 * @author Niko Schmuck
 */
public interface RecenzentRepository {

	void add(Recenzent nr);

	void remove(String id);

	Recenzent findById(String id);

	RecenzentSearchResult findAll();

	Long count();

}
