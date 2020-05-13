package com.ftgo.orderservice.event;

import com.ftgo.orderservice.api.event.OrderDomainEvent;
import com.ftgo.orderservice.model.Order;

import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

/**
 * The event publisher for order events.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderServiceEventPublisher extends AbstractAggregateDomainEventPublisher<Order, OrderDomainEvent> {
	public OrderServiceEventPublisher(DomainEventPublisher eventPublisher) {
		super(eventPublisher, Order.class, Order::getId);
	}
}
