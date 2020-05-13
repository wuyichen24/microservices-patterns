package com.ftgo.orderservice.event.model;

import com.ftgo.orderservice.api.model.OrderState;

import io.eventuate.tram.events.common.DomainEvent;

/**
 * The event about an order is started to be cancelled.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderCancelRequestedEvent implements DomainEvent {
	private OrderState state;

	public OrderCancelRequestedEvent(OrderState state) {
		this.state = state;
	}

	public OrderState getState() {
		return state;
	}
}
