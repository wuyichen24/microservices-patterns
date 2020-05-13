package com.ftgo.orderservice.event.model;

import com.ftgo.orderservice.controller.model.OrderRevision;

import io.eventuate.tram.events.common.DomainEvent;

public class OrderRevisionRejectedEvent implements DomainEvent {
	public OrderRevisionRejectedEvent(OrderRevision orderRevision) {
		throw new UnsupportedOperationException();
	}
}
