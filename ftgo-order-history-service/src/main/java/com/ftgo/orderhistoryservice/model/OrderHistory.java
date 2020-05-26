package com.ftgo.orderhistoryservice.model;

import java.util.List;
import java.util.Optional;

/**
 * The entity class for order history.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderHistory {
	private List<Order>      orders;
	private Optional<String> startKey;

	public OrderHistory(List<Order> orders, Optional<String> startKey) {
		this.orders   = orders;
		this.startKey = startKey;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public Optional<String> getStartKey() {
		return startKey;
	}
}
