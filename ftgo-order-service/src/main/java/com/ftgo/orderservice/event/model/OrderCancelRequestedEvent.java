package com.ftgo.orderservice.event.model;

import com.ftgo.orderservice.api.model.OrderState;

import io.eventuate.tram.events.common.DomainEvent;

public class OrderCancelRequestedEvent implements DomainEvent {
	private OrderState state;

	public OrderCancelRequestedEvent(OrderState state) {
		this.state = state;
	}

	public OrderState getState() {
		return state;
	}
}
