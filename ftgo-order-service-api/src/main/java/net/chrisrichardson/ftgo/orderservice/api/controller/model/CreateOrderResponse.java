package net.chrisrichardson.ftgo.orderservice.api.controller.model;

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
