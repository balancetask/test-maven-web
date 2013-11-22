package com.balancetask.yummy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

	@Bean
	public OrderService createService() {
		return new InMemoryOrderService();
	}

}
