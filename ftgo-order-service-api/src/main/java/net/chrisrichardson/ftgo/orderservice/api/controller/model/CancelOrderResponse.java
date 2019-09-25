package net.chrisrichardson.ftgo.orderservice.api.controller.model;

public class CancelOrderResponse {
	private String message;
	
	
	
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
