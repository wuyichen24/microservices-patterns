package com.ftgo.consumerservice.api.controller.model;

/**
 * The response for creating consumer API.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateConsumerResponse {
	private long consumerId;

	public CreateConsumerResponse() {}

	public CreateConsumerResponse(long consumerId) {
		this.consumerId = consumerId;
	}

	public long getConsumerId()                { return consumerId;            }
	public void setConsumerId(long consumerId) { this.consumerId = consumerId; }
}
