package com.myretail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.myretail.controller.ProductController;
import com.myretail.model.ProductDetails;

@Service
@Component
public class Receiver {

	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	ProductController productController;

	@KafkaListener(topics = "store1")
	public void receive(ProductDetails productDetails) {

		logger.info("received product='{}'", productDetails.toString());
		productController.createProduct(productDetails);

	}

}
