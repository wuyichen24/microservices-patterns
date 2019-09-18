package com.ftgo.orderhistoryservice.event.model;

import io.eventuate.tram.events.common.DomainEvent;

public class DeliveryPickedUpEvent implements DomainEvent {
	private String orderId;

	public String getOrderId() {
		return orderId;
	}
}
