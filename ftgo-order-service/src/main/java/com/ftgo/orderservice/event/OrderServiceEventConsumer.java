package com.ftgo.orderservice.event;

import com.ftgo.orderservice.service.OrderService;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.restaurantservice.api.event.RestaurantMenuRevisedEvent;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

public class OrderServiceEventConsumer {
	private OrderService orderService;

	public OrderServiceEventConsumer(OrderService orderService) {
		this.orderService = orderService;
	}

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder
				.forAggregateType("net.chrisrichardson.ftgo.restaurantservice.domain.Restaurant")
				.onEvent(RestaurantCreatedEvent.class, this::createMenu)
				.onEvent(RestaurantMenuRevisedEvent.class, this::reviseMenu).build();
	}

	private void createMenu(DomainEventEnvelope<RestaurantCreatedEvent> de) {
		String restaurantIds = de.getAggregateId();
		long id = Long.parseLong(restaurantIds);
		RestaurantMenu menu = de.getEvent().getMenu();
		orderService.createMenu(id, de.getEvent().getName(), menu);
	}

	public void reviseMenu(DomainEventEnvelope<RestaurantMenuRevisedEvent> de) {
		String restaurantIds = de.getAggregateId();
		long id = Long.parseLong(restaurantIds);
		RestaurantMenu revisedMenu = de.getEvent().getRevisedMenu();

		orderService.reviseMenu(id, revisedMenu);
	}
}
