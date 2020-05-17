package com.ftgo.restaurantservice.service;

import io.eventuate.tram.events.publisher.DomainEventPublisher;

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
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	public Restaurant create(CreateRestaurantRequest request) {
		Restaurant restaurant = new Restaurant(request.getName(), request.getMenu());
		restaurantRepository.save(restaurant);
		domainEventPublisher.publish(Restaurant.class, restaurant.getId(), Collections.singletonList(new RestaurantCreatedEvent(request.getName(), request.getAddress(), request.getMenu())));
		return restaurant;
	}

	public Optional<Restaurant> findById(long restaurantId) {
		return restaurantRepository.findById(restaurantId);
	}
}
