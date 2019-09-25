package com.ftgo.kitchenservice.event;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.kitchenservice.service.KitchenService;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.restaurantservice.api.event.RestaurantMenuRevisedEvent;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

public class KitchenServiceEventConsumer {
	@Autowired
	private KitchenService kitchenService;

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
		kitchenService.createMenu(id, menu);
	}

	public void reviseMenu(DomainEventEnvelope<RestaurantMenuRevisedEvent> de) {
		long id = Long.parseLong(de.getAggregateId());
		RestaurantMenu revisedMenu = de.getEvent().getRevisedMenu();
		kitchenService.reviseMenu(id, revisedMenu);
	}
}
