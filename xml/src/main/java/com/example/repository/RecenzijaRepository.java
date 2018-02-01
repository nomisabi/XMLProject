package com.example.repository;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.naucni_radovi.search.Product;
import com.example.model.naucni_radovi.search.ProductSearchResult;
import com.example.model.recenzija.Recenzija;
import com.example.model.recenzija.search.RecenzijaSearchResult;
import com.example.model.uloge.TKorisnik;

/**
 * Showcase for a simple repository allowing to access and modify
 * {@link Product} objects in a domain specific way.
 *
 * @author Niko Schmuck
 */
public interface RecenzijaRepository {

	void add(Recenzija recenzija);

	void remove(String id);

	Recenzija findById(String id);

	RecenzijaSearchResult findAll();

	Long count();

}
