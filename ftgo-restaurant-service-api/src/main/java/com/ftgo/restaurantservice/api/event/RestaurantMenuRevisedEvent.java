package com.ftgo.restaurantservice.api.event;

import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import io.eventuate.tram.events.common.DomainEvent;

/**
 * The event about a restaurant's menu has been revised.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class RestaurantMenuRevisedEvent implements DomainEvent {
	private RestaurantMenu menu;

	public RestaurantMenu getRevisedMenu() {
		return menu;
	}
}
