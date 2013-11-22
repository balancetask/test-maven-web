package com.balancetask.yummy.test;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.balancetask.yummy.Order;

import java.io.IOException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class OrderFunctionalTest {

	static HttpHeaders getHeaders(String auth) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
		headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

		return headers;
	}

	@Test
	public void thatOrdersCanBeAddedAndQueried() throws IOException {
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				OrderServiceFixtures.orderCreatedJson(), getHeaders("letsnosh" + ":" + "noshing"));

		RestTemplate template = new RestTemplate();
		ResponseEntity<Order> entity = template.postForEntity(
				"http://localhost:8080/test-maven-web/yummy/orders", requestEntity, Order.class);

		String path = entity.getHeaders().getLocation().getPath();

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		assertTrue(path.startsWith("/test-maven-web/yummy/orders/"));
		Order order = entity.getBody();

		System.out.println("The Order ID is " + order.getKey());
		System.out.println("The Location is " + entity.getHeaders().getLocation());

		assertEquals(2, order.getOrderItems().size());
	}

	@Test
	public void thatOrdersCannotBeAddedAndQueriedWithBadUser() throws IOException {

		HttpEntity<String> requestEntity = new HttpEntity<String>(
				OrderServiceFixtures.orderCreatedJson(), getHeaders("letsnosh" + ":" + "BADPWRD"));

		RestTemplate template = new RestTemplate();
		try {
			ResponseEntity<Order> entity = template
					.postForEntity("http://localhost:8080/test-maven-web/yummy/orders",
							requestEntity, Order.class);

			fail("Request Passed incorrectly with status " + entity.getStatusCode());
		}
		catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
		}
	}
}
