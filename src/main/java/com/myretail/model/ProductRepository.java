package com.myretail.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends MongoRepository <ProductDetails, Integer>{
	
	ProductDetails findProductById(@Param("id")Integer id);
	
}
