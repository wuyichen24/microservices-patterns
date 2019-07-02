package net.chrisrichardson.ftgo.orderservice.api.web;

import java.util.Map;

public class ReviseOrderRequest {
	private String name;

		
  private Map<String, Integer> revisedLineItemQuantities;

  private ReviseOrderRequest() {
  }

  public ReviseOrderRequest(Map<String, Integer> revisedLineItemQuantities) {
    this.revisedLineItemQuantities = revisedLineItemQuantities;
  }

  public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
  
  public Map<String, Integer> getRevisedLineItemQuantities() {
    return revisedLineItemQuantities;
  }

  public void setRevisedLineItemQuantities(Map<String, Integer> revisedLineItemQuantities) {
    this.revisedLineItemQuantities = revisedLineItemQuantities;
  }
}
