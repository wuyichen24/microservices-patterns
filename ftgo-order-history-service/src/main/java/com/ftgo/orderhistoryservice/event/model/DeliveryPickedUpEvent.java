package com.ftgo.orderhistoryservice.event.model;

import io.eventuate.tram.events.common.DomainEvent;

/**
 * The event about a delivery has been picked up.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
public class DeliveryPickedUpEvent implements DomainEvent {
	private String orderId;

	public String getOrderId() {
		return orderId;
	}
}
