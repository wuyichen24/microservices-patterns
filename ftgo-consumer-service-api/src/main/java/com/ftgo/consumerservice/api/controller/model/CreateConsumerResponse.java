package com.ftgo.consumerservice.api.controller.model;

public class CreateConsumerResponse {
	private long consumerId;

	public CreateConsumerResponse() {}

	public CreateConsumerResponse(long consumerId) {
		this.consumerId = consumerId;
	}

	public long getConsumerId()                { return consumerId;            }
	public void setConsumerId(long consumerId) { this.consumerId = consumerId; }
}
