package com.ftgo.orderservice.api.controller.model;

/**
 * The request for canceling an order API.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class CancelOrderRequest {
	private String name;

	public CancelOrderRequest() {}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
