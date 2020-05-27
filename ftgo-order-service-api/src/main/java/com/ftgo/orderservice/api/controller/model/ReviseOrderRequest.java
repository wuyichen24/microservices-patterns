package com.ftgo.orderservice.api.controller.model;

import java.util.Map;

/**
 * The request for revising an order API.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class ReviseOrderRequest {
	private String               name;
	private Map<String, Integer> revisedLineItemQuantities;

	public ReviseOrderRequest() {}
	
	public ReviseOrderRequest(Map<String, Integer> revisedLineItemQuantities) {
		this.revisedLineItemQuantities = revisedLineItemQuantities;
	}

	public String               getName()                                                                    { return name;                                                }
	public void                 setName(String name)                                                         { this.name = name;                                           }
	public Map<String, Integer> getRevisedLineItemQuantities()                                               { return revisedLineItemQuantities;                           }
	public void                 setRevisedLineItemQuantities(Map<String, Integer> revisedLineItemQuantities) { this.revisedLineItemQuantities = revisedLineItemQuantities; }
}
