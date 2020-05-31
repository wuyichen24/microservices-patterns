/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ftgo.kitchenservice.event;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.kitchenservice.service.KitchenService;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.restaurantservice.api.event.RestaurantMenuRevisedEvent;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

/**
 * The event handlers for incoming events.
 *
 * @author  Wuyi Chen
 * @date    04/14/2020
 * @version 1.0
 * @since   1.0
 */
public class KitchenServiceEventConsumer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KitchenService kitchenService;

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder
				.forAggregateType("com.ftgo.restaurantservice.model.Restaurant")
				.onEvent(RestaurantCreatedEvent.class,     this::createMenu)
				.onEvent(RestaurantMenuRevisedEvent.class, this::reviseMenu).build();
	}

	/**
	 * The listener for {@code RestaurantCreatedEvent}.
	 * 
	 * @param  de
	 *         The event envelope of {@code RestaurantCreatedEvent}.
	 */
	private void createMenu(DomainEventEnvelope<RestaurantCreatedEvent> de) {
		logger.debug("Receive RestaurantCreatedEvent");
		
		String restaurantIds = de.getAggregateId();
		long id = Long.parseLong(restaurantIds);
		RestaurantMenu menu = de.getEvent().getMenu();
		kitchenService.createMenu(id, menu);
	}

	/**
	 * The listener for {@code RestaurantMenuRevisedEvent}.
	 * 
	 * @param  de
	 *         The event envelope of {@code RestaurantMenuRevisedEvent}.
	 */
	public void reviseMenu(DomainEventEnvelope<RestaurantMenuRevisedEvent> de) {
		logger.debug("Receive RestaurantMenuRevisedEvent");
		
		long id = Long.parseLong(de.getAggregateId());
		RestaurantMenu revisedMenu = de.getEvent().getRevisedMenu();
		kitchenService.reviseMenu(id, revisedMenu);
	}
}
