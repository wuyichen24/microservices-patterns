package net.chrisrichardson.ftgo.orderservice.api.controller.model;

public class ReviseOrderResponse {
	private String message;

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
