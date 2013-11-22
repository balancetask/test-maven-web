package com.balancetask.yummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InMemoryOrderService implements OrderService {

	List<OrderDetails> orders;

	public InMemoryOrderService() {
		orders = new ArrayList<OrderDetails>();

		/*
		Map<String, Integer> items = new HashMap<String, Integer>();
		items.put("Default 1", 1);
		items.put("Default 2", 2);
		
		OrderDetails order = new OrderDetails();
		order.setDateTimeOfSubmission(new Date());
		order.setOrderItems(items);
		
		orders.add(order);
		*/
	}
	
	public List<OrderDetails> requestAllOrders() {
		return orders;
	}

	public OrderDetails requestOrderDetails(UUID key) {
		for (OrderDetails o : orders) {
			if (o.getKey() == key)
				return o;
		}
		return null;
	}

	public OrderStatusDetails requestOrderStatus(UUID key) {
		for (OrderDetails o : orders) {
			if (o.getKey() == key)
				return new OrderStatusDetails(new Date(), "OK");
		}
		return null;
	}

	public OrderDetails createOrder(OrderDetails order) {
		return orders.add(order) ? order : null;
	}

	public OrderDetails deleteOrder(UUID key) {
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getKey() == key)
				return orders.remove(i);
		}
		return null;
	}

}
 