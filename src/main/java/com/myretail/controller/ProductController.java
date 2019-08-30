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
import com.myretail.service.Sender;

@RestController
@RequestMapping("/products")
@EnableMongoRepositories(basePackageClasses = ProductRepository.class)
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	Sender sender;

	// KafKa Queue Implemented
	@RequestMapping(value = "/admin/bulk", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void bulkInsertProduct(@RequestBody List<ProductDetails> productList) {
		productList.forEach(product -> this.sender.send(product));
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
		ProductDetails prodDetails = productRepository.findProductById(Integer.parseInt(id));
		if (product.getId() == null)
			product.setId(prodDetails.getId());
		if (product.getProduct_description() == null)
			product.setProduct_description(prodDetails.getProduct_description());
		if (product.getCurrent_price() == null)
			product.setCurrent_price(prodDetails.getCurrent_price());
		if (product.getCurrency_code() == null)
			product.setCurrency_code(prodDetails.getCurrency_code());

		return productRepository.save(product);
	}

	// Retrieve All Products
	@RequestMapping(value = "/")
	public List<ProductDetails> retrieveProductDetails() {
		return productRepository.findAll();
	}

}
