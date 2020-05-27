package com.ftgo.orderservice.api.controller.model;

/**
 * The response for canceling an order API.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class CancelOrderResponse {
	private String message;

	public CancelOrderResponse() {}
	
	public String getMessage() {
		return message;
	}

	public CancelOrderResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public static CancelOrderResponse newBuilder() {
		return new CancelOrderResponse();
	}

	public CancelOrderResponse build() {
		return this;
	}
}
