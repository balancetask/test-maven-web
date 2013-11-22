package com.balancetask.yummy;

import java.util.List;
import java.util.UUID;

public interface OrderService {

	public List<OrderDetails> requestAllOrders();

	public OrderDetails requestOrderDetails(UUID key);

	public OrderStatusDetails requestOrderStatus(UUID key);

	public OrderDetails createOrder(OrderDetails order);

	public OrderDetails deleteOrder(UUID key);

}
