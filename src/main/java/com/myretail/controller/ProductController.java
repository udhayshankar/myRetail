package com.myretail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.model.ProductDetails;
import com.myretail.model.ProductRepository;


@RestController
@RequestMapping("/products")
@EnableMongoRepositories(basePackageClasses = ProductRepository.class)
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
	//Insert single data
	@RequestMapping(value = "/",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createProduct(@RequestBody ProductDetails productDetails) {
		productRepository.save(productDetails);
	}
	
	@RequestMapping(value = "/bulk",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void bulkInsertProduct(@RequestBody List<ProductDetails> productList) {
		productRepository.saveAll(productList);
	}

	//Get Product By ID
	@RequestMapping(value ="/{id}", method = RequestMethod.GET)
	public ProductDetails getProductByID(@PathVariable String id) {
 		return productRepository.findProductById(Integer.parseInt(id));	
		
	}
	
	//Update Product By ID
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateProductByID(@PathVariable String id , @RequestBody  ProductDetails product) {
		productRepository.save(product);
	}
	
	//Delete Product By ID
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable String id) {
		productRepository.deleteById(Integer.parseInt(id));
	}
	
	//Retrieve All Products
	@RequestMapping(value="/")
	public List<ProductDetails> retrieveProductDetails(){
		return productRepository.findAll();
		
	}
	
	
	
	
	
}
