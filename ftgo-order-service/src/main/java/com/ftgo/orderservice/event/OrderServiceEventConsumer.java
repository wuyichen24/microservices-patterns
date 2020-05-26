package com.ftgo.orderservice.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftgo.orderservice.service.OrderService;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.restaurantservice.api.event.RestaurantMenuRevisedEvent;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

/**
 * The event consumer for subscribed events.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderServiceEventConsumer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private OrderService orderService;

	public OrderServiceEventConsumer(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * Defines which aggregate's event is subscribed and which events are subscribed.
	 * 
	 * @return  The {@code DomainEventHandlers} object.
	 */
	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder
				.forAggregateType("com.ftgo.restaurantservice.model.Restaurant")
				.onEvent(RestaurantCreatedEvent.class, this::createMenu)
				.onEvent(RestaurantMenuRevisedEvent.class, this::reviseMenu).build();
	}

	private void createMenu(DomainEventEnvelope<RestaurantCreatedEvent> de) {
		logger.debug("Receive RestaurantCreatedEvent");
		
		String restaurantIds = de.getAggregateId();
		long id = Long.parseLong(restaurantIds);
		RestaurantMenu menu = de.getEvent().getMenu();
		orderService.createMenu(id, de.getEvent().getName(), menu);
	}

	public void reviseMenu(DomainEventEnvelope<RestaurantMenuRevisedEvent> de) {
		logger.debug("Receive RestaurantMenuRevisedEvent");
		
		String restaurantIds = de.getAggregateId();
		long id = Long.parseLong(restaurantIds);
		RestaurantMenu revisedMenu = de.getEvent().getRevisedMenu();
		orderService.reviseMenu(id, revisedMenu);
	}
}
