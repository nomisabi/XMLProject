package com.example.service;

import com.example.domain.Product;
import com.example.domain.ProductSearchResult;

/**
 * Showcase for a simple repository allowing to access and modify
 * {@link Product} objects in a domain specific way.
 *
 * @author Niko Schmuck
 */
public interface ProductRepository {

	void add(Product product);

	void remove(Long sku);

	Product findBySku(Long sku);

	ProductSearchResult findAll();

	ProductSearchResult findByName(String name);

	ProductSearchResult findByYear(int year);
	// TODO: add ProductSearchResult findByPriceRange(Double min, Double max);
	// TODO: add ProductSearchResult findByCategory(String category);

	// TODO: add List<Category> findAllDistinctCategories();

	Long count();

}
