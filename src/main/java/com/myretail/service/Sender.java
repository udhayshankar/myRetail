package com.myretail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.myretail.model.ProductDetails;

@Service
public class Sender {

	private static final Logger logger = LoggerFactory.getLogger(Sender.class);

	@Value("${spring.kafka.template.default-topic}")
	private String TOPIC = "store1";

	@Autowired
	private KafkaTemplate<String, ProductDetails> kafkaTemplate;

	public void send(ProductDetails product) {
		logger.info("sent product='{}'", product.toString());
		// adding product one by one
		this.kafkaTemplate.send(TOPIC, product);

	}
}
