package com.ftgo.restaurantservice.api.event;

import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import io.eventuate.tram.events.common.DomainEvent;

public class RestaurantMenuRevisedEvent implements DomainEvent {
	private RestaurantMenu menu;

	public RestaurantMenu getRevisedMenu() {
		return menu;
	}
}
