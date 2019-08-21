package com.myretail.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myretail.model.ProductDetails;
import com.myretail.model.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyRetailApplicationTests {

	@Autowired
	private ProductRepository prodRepository;
	
	
	@Before
	public void setup() throws Exception
	{
		ProductDetails productDetails = new ProductDetails();
			productDetails.setId(12456);
			productDetails.setProduct_description("pant");
			productDetails.setCurrency_code("USD");
			productDetails.setCurrent_price(345.78);
			assertNull(productDetails.getId());
			prodRepository.save(productDetails);
		    assertNotNull(productDetails.getId());	
	}
	/*@Test
	public void getData() {
		Optional<ProductDetails> product = prodRepository.findById(12456);
		assertNotNull(product);
		assertEquals("USD", prodRepository.findById(12456).get().getCurrency_code());
	}*/

}
