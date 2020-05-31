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
package com.ftgo.restaurantservice.service;

import io.eventuate.tram.events.publisher.DomainEventPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ftgo.restaurantservice.api.controller.model.CreateRestaurantRequest;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.restaurantservice.model.Restaurant;
import com.ftgo.restaurantservice.repository.RestaurantRepository;

import java.util.Collections;
import java.util.Optional;

/**
 * The restaurant service class for creating and managing restaurants.
 *
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@Transactional
public class RestaurantService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	public Restaurant create(CreateRestaurantRequest request) {
		Restaurant restaurant = new Restaurant(request.getName(), request.getMenu());
		restaurantRepository.save(restaurant);
		domainEventPublisher.publish(Restaurant.class, restaurant.getId(), Collections.singletonList(new RestaurantCreatedEvent(request.getName(), request.getAddress(), request.getMenu())));
		logger.debug("Send RestaurantCreatedEvent to Restaurant event channel");
		return restaurant;
	}

	public Optional<Restaurant> findById(long restaurantId) {
		return restaurantRepository.findById(restaurantId);
	}
}
