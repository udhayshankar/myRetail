package com.myretail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.myretail.model.ProductDetails;

public interface ProductRepository extends MongoRepository<ProductDetails, Integer> {

	ProductDetails findProductById(@Param("id") Integer id);

}
