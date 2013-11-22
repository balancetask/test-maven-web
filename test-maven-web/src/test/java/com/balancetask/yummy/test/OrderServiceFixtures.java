package com.balancetask.yummy.test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.balancetask.yummy.OrderDetails;
import com.balancetask.yummy.OrderStatusDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderServiceFixtures {
	public static OrderStatusDetails orderStatusNotFound(UUID key) {
		return new OrderStatusDetails(new Date(), "Not Found");
	}

	public static OrderStatusDetails orderStatus(UUID key, String status) {
		return new OrderStatusDetails(new Date(), status);
	}

	public static OrderDetails orderDetailsNotFound(UUID key) {
		return null; 
	}

	public static OrderDetails orderDetails(UUID key) {
		Map<String, Integer> orderItems = new HashMap<String, Integer>();
		orderItems.put("Item 1", 1);
		orderItems.put("Item 2", 2);
		
		OrderDetails o = new OrderDetails(key);
		o.setDateTimeOfSubmission(new Date());
		o.setOrderItems(orderItems);
		return o;
	}

	public static OrderDetails orderCreated(UUID key) {
		return orderDetails(key);
	}

	public static OrderDetails orderDeleted(UUID key) {
		return orderDetails(key);
	}

	public static OrderDetails orderDeletedFailed(UUID key) {
		return null;
	}

	public static OrderDetails orderDeletedNotFound(UUID key) {
		return null;
	}
	
	public static String orderCreatedJson() throws IOException {
		OrderDetails order = orderCreated(UUID.randomUUID());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(order);
	}
}
