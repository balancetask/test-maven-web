package com.balancetask.yummy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/yummy/orders")
public class OrderQueriesController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Order> getAllOrders() {
		List<Order> orders = new ArrayList<Order>();
		for (OrderDetails detail : orderService.requestAllOrders()) {
			orders.add(Order.fromOrderDetails(detail));
		}
		return orders;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Order> viewOrder(@PathVariable String id) {

		OrderDetails details = orderService.requestOrderDetails(UUID.fromString(id));

		if (details == null) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}

		Order order = Order.fromOrderDetails(details);

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

}
