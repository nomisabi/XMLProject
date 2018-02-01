package com.example.repository;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.model.uloge.Autor;
import com.example.model.uloge.TKorisnik;
import com.example.model.uloge.Urednik;
import com.example.model.uloge.search.AutorSearchResult;

/**
 * Showcase for a simple repository allowing to access and modify
 * {@link Product} objects in a domain specific way.
 *
 * @author Niko Schmuck
 */
public interface UrednikRepository {

	void add(Urednik nr);

	void remove(String id);

	Urednik findById(String id);


}
