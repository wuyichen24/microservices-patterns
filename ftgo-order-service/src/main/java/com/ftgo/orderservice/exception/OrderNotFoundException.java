package com.ftgo.orderservice.exception;

/**
 * The exception to indicate that the order can not be found by ID.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(Long orderId) {
		super("Order not found" + orderId);
	}
}
