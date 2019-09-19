package com.ftgo.orderservice.event.model;

import io.eventuate.tram.events.common.DomainEvent;

public class OrderLineItemChangeQueuedEvent implements DomainEvent {
	public OrderLineItemChangeQueuedEvent(String lineItemId, int newQuantity) {

	}
}
