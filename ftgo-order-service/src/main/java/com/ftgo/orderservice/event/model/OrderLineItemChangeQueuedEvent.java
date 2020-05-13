package com.ftgo.orderservice.event.model;

import io.eventuate.tram.events.common.DomainEvent;

/**
 * The event about an order has been updated for items.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderLineItemChangeQueuedEvent implements DomainEvent {
	public OrderLineItemChangeQueuedEvent(String lineItemId, int newQuantity) {

	}
}
