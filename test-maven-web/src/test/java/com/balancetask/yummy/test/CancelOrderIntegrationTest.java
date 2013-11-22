package com.balancetask.yummy.test;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.balancetask.yummy.OrderCommandsController;
import com.balancetask.yummy.OrderService;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CancelOrderIntegrationTest {

	MockMvc mockMvc;

	@InjectMocks
	OrderCommandsController controller;

	@Mock
	OrderService orderService;

	UUID key = UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13");

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = standaloneSetup(controller).setMessageConverters(
				new MappingJackson2HttpMessageConverter()).build();

	}

	@Test
	public void thatDeleteOrderUsesHttpOkOnSuccess() throws Exception {

		when(orderService.deleteOrder(any(UUID.class))).thenReturn(
				OrderServiceFixtures.orderDeleted(key));

		this.mockMvc
				.perform(
						delete("/yummy/orders/{id}", key.toString())
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());

		verify(orderService).deleteOrder(argThat(Matchers.equalTo(key)));
	}

	@Test
	public void thatDeleteOrderUsesHttpNotFoundOnEntityLookupFailure()
			throws Exception {

		when(orderService.deleteOrder(any(UUID.class))).thenReturn(
				OrderServiceFixtures.orderDeletedNotFound(key));

		this.mockMvc
				.perform(
						delete("/yummy/orders/{id}", key.toString())
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound());

	}

	// TODO: commented out since FORBIDDEN return isn't currently supported
	// @Test
	public void thatDeleteOrderUsesHttpForbiddenOnEntityDeletionFailure()
			throws Exception {

		when(orderService.deleteOrder(any(UUID.class))).thenReturn(
				OrderServiceFixtures.orderDeletedFailed(key));

		this.mockMvc
				.perform(
						delete("/yummy/orders/{id}", key.toString())
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isForbidden());
	}
}
