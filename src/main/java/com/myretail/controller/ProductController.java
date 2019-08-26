package com.myretail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.model.ProductDetails;
import com.myretail.repository.ProductRepository;

@RestController
@RequestMapping("/products")
@EnableMongoRepositories(basePackageClasses = ProductRepository.class)
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@RequestMapping(value = "/admin/bulk", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

	public void bulkInsertProduct(@RequestBody List<ProductDetails> productList) {
		productRepository.saveAll(productList);
	}

	// Insert single data
	@RequestMapping(value = "/admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createProduct(@RequestBody ProductDetails productDetails) {
		productRepository.save(productDetails);
	}

	// Get Product By ID Cacheable
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@Cacheable(value = "productdetailsCache", key = "#id")
	public ProductDetails getProductByID(@PathVariable String id) {
		return productRepository.findProductById(Integer.parseInt(id));

	}

	// Delete Product By ID Cachedelete
	@RequestMapping(value = "/admin/{id}", method = RequestMethod.DELETE)
	@CacheEvict(value = "productdetailsCache", key = "#id")
	public void deleteProduct(@PathVariable String id) {
		productRepository.deleteById(Integer.parseInt(id));
	}

	// Update Product By ID
	@RequestMapping(value = "/admin/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CachePut(value = "productdetailsCache", key = "#id")
	public ProductDetails updateProductByID(@PathVariable String id, @RequestBody ProductDetails product) {
		return productRepository.save(product);
	}

	// Retrieve All Products
	@RequestMapping(value = "/")
	public List<ProductDetails> retrieveProductDetails() {
		return productRepository.findAll();
	}

}
