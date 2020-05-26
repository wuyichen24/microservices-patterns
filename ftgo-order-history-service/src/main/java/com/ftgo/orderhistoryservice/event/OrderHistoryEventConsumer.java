package com.ftgo.orderhistoryservice.event;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.dao.dynamodb.SourceEvent;
import com.ftgo.orderhistoryservice.event.model.DeliveryPickedUpEvent;
import com.ftgo.orderhistoryservice.model.Location;
import com.ftgo.orderhistoryservice.model.Order;
import com.ftgo.orderservice.api.event.OrderCreatedEvent;
import com.ftgo.orderservice.api.model.OrderState;

import java.util.Optional;

/**
 * The event consumer for subscribed events.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderHistoryEventConsumer {
	private OrderHistoryDao orderHistoryDao;
	private String          orderId;
	private Order           order;
	private Location        location; 
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public OrderHistoryEventConsumer(OrderHistoryDao orderHistoryDao) {
		this.orderHistoryDao = orderHistoryDao;
	}

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder.forAggregateType("com.ftgo.orderservice.model.Order")
				.onEvent(OrderCreatedEvent.class, this::handleOrderCreated)
				.build();
	}

	private Optional<SourceEvent> makeSourceEvent(DomainEventEnvelope<?> dee) {
		return Optional.of(new SourceEvent(dee.getAggregateType(), dee.getAggregateId(), dee.getEventId()));
	}

	public void handleOrderCreated(DomainEventEnvelope<OrderCreatedEvent> dee) {
		logger.debug("Receive OrderCreatedEvent");
		orderHistoryDao.addOrder(makeOrder(dee.getAggregateId(), dee.getEvent()), makeSourceEvent(dee));
	}

	private Order makeOrder(String orderId, OrderCreatedEvent event) {
		return new Order(orderId, Long.toString(event.getOrderDetails().getConsumerId()), OrderState.APPROVAL_PENDING,
				event.getOrderDetails().getLineItems(), event.getOrderDetails().getOrderTotal(),
				event.getOrderDetails().getRestaurantId(), event.getRestaurantName());
	}

	public void handleDeliveryPickedUp(DomainEventEnvelope<DeliveryPickedUpEvent> dee) {
		orderHistoryDao.notePickedUp(dee.getEvent().getOrderId(), makeSourceEvent(dee));
	}
}
