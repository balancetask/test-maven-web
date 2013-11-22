package com.balancetask.yummy;

import com.balancetask.yummy.OrderDetails;

import org.springframework.beans.BeanUtils;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {

	private final Date dateTimeOfSubmission;
	private Map<String, Integer> orderItems;
	private UUID key;

	private OrderStatus status;
	private List<OrderStatus> statusHistory;

	public Order() {
		this(new Date());
	}

	public Order(final Date dateTimeOfSubmission) {
		this.key = UUID.randomUUID();
		this.dateTimeOfSubmission = dateTimeOfSubmission;
		statusHistory = new ArrayList<OrderStatus>();
	}

	public void addStatus(OrderStatus newStatus) {
		statusHistory.add(newStatus);
		status = newStatus;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public Date getDateTimeOfSubmission() {
		return dateTimeOfSubmission;
	}

	public UUID getKey() {
		return key;
	}

	public void setKey(UUID key) {
		this.key = key;
	}

	public void setOrderItems(Map<String, Integer> orderItems) {
		if (orderItems == null) {
			this.orderItems = Collections.emptyMap();
		} else {
			this.orderItems = Collections.unmodifiableMap(orderItems);
		}
	}

	public Map<String, Integer> getOrderItems() {
		return orderItems;
	}

	public boolean canBeDeleted() {
		return true;
	}

	public OrderDetails toOrderDetails() {
		OrderDetails details = new OrderDetails();

		BeanUtils.copyProperties(this, details);

		return details;
	}

	public static Order fromOrderDetails(OrderDetails orderDetails) {
		Order order = new Order(orderDetails.getDateTimeOfSubmission());

		BeanUtils.copyProperties(orderDetails, order);

		return order;
	}
}
