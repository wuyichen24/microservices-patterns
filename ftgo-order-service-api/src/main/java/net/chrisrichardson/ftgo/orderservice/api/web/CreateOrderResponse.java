package net.chrisrichardson.ftgo.orderservice.api.web;

public class CreateOrderResponse {
  private long orderId;

  public long getOrderId() {
    return orderId;
  }

  public CreateOrderResponse setOrderId(long orderId) {
    this.orderId = orderId;
    return this;
  }

  private CreateOrderResponse() {
  }

  public CreateOrderResponse(long orderId) {
    this.orderId = orderId;
  }

  public static CreateOrderResponse newBuilder() {
	return new CreateOrderResponse();
  }
  
  public CreateOrderResponse build() {
	  return this;
  }
}
