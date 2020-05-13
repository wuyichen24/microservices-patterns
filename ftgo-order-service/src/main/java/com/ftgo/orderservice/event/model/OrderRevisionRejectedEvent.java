package com.ftgo.orderservice.event.model;

import com.ftgo.orderservice.controller.model.OrderRevision;

import io.eventuate.tram.events.common.DomainEvent;

/**
 * The event of the revision of an order has been rejected.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderRevisionRejectedEvent implements DomainEvent {
	public OrderRevisionRejectedEvent(OrderRevision orderRevision) {
		// TODO
		throw new UnsupportedOperationException();
	}
}
