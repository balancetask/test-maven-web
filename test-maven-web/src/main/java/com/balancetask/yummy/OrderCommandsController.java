package com.balancetask.yummy;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/yummy/orders")
public class OrderCommandsController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Order> createOrder(@RequestBody Order order, UriComponentsBuilder builder) {

		OrderDetails orderCreated = orderService.createOrder(order.toOrderDetails());

		Order newOrder = Order.fromOrderDetails(orderCreated);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/yummy/orders/{id}")
				.buildAndExpand(orderCreated.getKey().toString()).toUri());

		return new ResponseEntity<Order>(newOrder, headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Order> cancelOrder(@PathVariable String id) {

		OrderDetails orderDeleted = orderService.deleteOrder(UUID.fromString(id));

		if (orderDeleted == null) {
			// TODO: may need to distinguish between NotFound (item doesn't
			// exist) and
			// Failed (item found but couldn't be deleted due to permission
			// issue, etc)
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}

		Order order = Order.fromOrderDetails(orderDeleted);

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
}
