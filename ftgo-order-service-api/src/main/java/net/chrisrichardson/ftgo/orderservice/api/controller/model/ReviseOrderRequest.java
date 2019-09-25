package net.chrisrichardson.ftgo.orderservice.api.controller.model;

import java.util.Map;

public class ReviseOrderRequest {
	private String               name;
	private Map<String, Integer> revisedLineItemQuantities;

	public ReviseOrderRequest(Map<String, Integer> revisedLineItemQuantities) {
		this.revisedLineItemQuantities = revisedLineItemQuantities;
	}

	public String               getName()                                                                    { return name;                                                }
	public void                 setName(String name)                                                         { this.name = name;                                           }
	public Map<String, Integer> getRevisedLineItemQuantities()                                               { return revisedLineItemQuantities;                           }
	public void                 setRevisedLineItemQuantities(Map<String, Integer> revisedLineItemQuantities) { this.revisedLineItemQuantities = revisedLineItemQuantities; }
}
