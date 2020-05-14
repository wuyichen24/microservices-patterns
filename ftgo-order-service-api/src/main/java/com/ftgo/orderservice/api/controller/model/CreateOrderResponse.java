package com.ftgo.orderservice.api.controller.model;

/**
 * The response for creating an order API.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateOrderResponse {
	private long orderId;

	private CreateOrderResponse() {}
	
	public CreateOrderResponse(long orderId) {
		this.orderId = orderId;
	}
	
	public long getOrderId() {
		return orderId;
	}

	public CreateOrderResponse setOrderId(long orderId) {
		this.orderId = orderId;
		return this;
	}

	public static CreateOrderResponse newBuilder() {
		return new CreateOrderResponse();
	}

	public CreateOrderResponse build() {
		return this;
	}
}
