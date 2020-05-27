package com.ftgo.orderservice.api.controller.model;

/**
 * The response for revising an order API.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class ReviseOrderResponse {
	private String message;

	public ReviseOrderResponse() {}
	
	public String getMessage() {
		return message;
	}

	public ReviseOrderResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public static ReviseOrderResponse newBuilder() {
		return new ReviseOrderResponse();
	}

	public ReviseOrderResponse build() {
		return this;
	}
}
