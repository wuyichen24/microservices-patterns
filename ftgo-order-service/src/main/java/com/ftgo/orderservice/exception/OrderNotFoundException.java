package com.ftgo.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException(Long orderId) {
		super("Order not found" + orderId);
	}
}
