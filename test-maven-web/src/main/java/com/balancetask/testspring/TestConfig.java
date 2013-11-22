package com.balancetask.testspring;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

	@Bean
	ProductManager createProductManager() {
		List<Product> list = new ArrayList<Product>();
		list.add(new Product("BalanceTask", 3.99));
		list.add(new Product("Hedgehog", 2.99));

		SimpleProductManager m = new SimpleProductManager();
		m.setProducts(list);
		
		return m;
	}
	
}
