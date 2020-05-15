package com.ftgo.apigateway.exception;

/**
 * The exception to indicate that the order cannot be found.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException() {
	}
}
