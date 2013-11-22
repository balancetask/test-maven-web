package com.balancetask.yummy.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.balancetask.yummy.OrderQueriesController;
import com.balancetask.yummy.OrderService;

import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class ViewOrderIntegrationTest {

	MockMvc mockMvc;

	@InjectMocks
	OrderQueriesController controller;

	@Mock
	OrderService orderService;

	UUID key = UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13");

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setMessageConverters(new MappingJackson2HttpMessageConverter())
				.build();
	}

	@Test
	public void thatViewOrderUsesHttpNotFound() throws Exception {

		when(orderService.requestOrderDetails(any(UUID.class))).thenReturn(
				OrderServiceFixtures.orderDetailsNotFound(key));

		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/yummy/orders/{id}",
								key.toString()).accept(
								MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void thatViewOrderUsesHttpOK() throws Exception {

		when(orderService.requestOrderDetails(any(UUID.class))).thenReturn(
				OrderServiceFixtures.orderDetails(key));

		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/yummy/orders/{id}",
						key.toString()).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void thatViewOrderRendersCorrectly() throws Exception {

		when(orderService.requestOrderDetails(any(UUID.class))).thenReturn(
				OrderServiceFixtures.orderDetails(key));

		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/yummy/orders/{id}",
								key.toString()).accept(
								MediaType.APPLICATION_JSON))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.orderItems['Item 1']")
								.value(1))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.orderItems['Item 2']")
								.value(2))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.key").value(
								key.toString()));
	}
}