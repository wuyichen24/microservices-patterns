package com.ftgo.orderservice.event;

import com.ftgo.orderservice.api.event.OrderDomainEvent;
import com.ftgo.orderservice.model.Order;

import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

public class OrderDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Order, OrderDomainEvent> {
	public OrderDomainEventPublisher(DomainEventPublisher eventPublisher) {
		super(eventPublisher, Order.class, Order::getId);
	}
}
