package com.myretail.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myretail.model.ProductDetails;
import com.myretail.myRetailapplication.MyRetailApplication;
import com.myretail.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyRetailApplication.class)
public class MyRetailApplicationTests {

	@Autowired
	private ProductRepository prodRepository;

	@Before
	public void setup() throws Exception {
		ProductDetails productDetails = new ProductDetails();
		productDetails.setProduct_description("jean");
		productDetails.setCurrency_code("usd");
		productDetails.setCurrent_price(30.78);
		productDetails.setId(58397);
		prodRepository.save(productDetails);
		assertNotNull(productDetails.getId());
	}

	@Test
	public void getData() {
		ProductDetails productDetails = prodRepository.findProductById(Integer.parseInt("58397"));
		assertNotNull(productDetails);
		assertEquals("usd", productDetails.getCurrency_code());
	}

	@Test
	public void updateData() {
		Double price = new Double(345.78);
		ProductDetails productDetails = prodRepository.findProductById(Integer.parseInt("58397"));
		productDetails.setCurrent_price(price);
		prodRepository.save(productDetails);
		assertEquals(price, prodRepository.findProductById(Integer.parseInt("58397")).getCurrent_price());

	}

	@After
	public void tearDown() {
		ProductDetails productDetails = prodRepository.findById(Integer.parseInt("58397")).get();
		prodRepository.delete(productDetails);
	}

}
