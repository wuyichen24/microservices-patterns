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
package com.ftgo.deliveryservice.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.ftgo.common.model.Address;
import com.ftgo.deliveryservice.api.model.ActionInfo;
import com.ftgo.deliveryservice.api.model.DeliveryInfo;
import com.ftgo.deliveryservice.api.model.DeliveryStatus;
import com.ftgo.deliveryservice.model.Action;
import com.ftgo.deliveryservice.model.Courier;
import com.ftgo.deliveryservice.model.Delivery;
import com.ftgo.deliveryservice.model.Restaurant;
import com.ftgo.deliveryservice.repository.CourierRepository;
import com.ftgo.deliveryservice.repository.DeliveryRepository;
import com.ftgo.deliveryservice.repository.RestaurantRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The delivery service class for creating and managing deliveries.
 *
 * @author  Wuyi Chen
 * @date    04/14/2020
 * @version 1.0
 * @since   1.0
 */
public class DeliveryService {
	private RestaurantRepository restaurantRepository;
	private DeliveryRepository   deliveryRepository;
	private CourierRepository    courierRepository;
	
	private Random               random = new Random();

	public DeliveryService(RestaurantRepository restaurantRepository, DeliveryRepository deliveryRepository, CourierRepository courierRepository) {
		this.restaurantRepository = restaurantRepository;
		this.deliveryRepository   = deliveryRepository;
		this.courierRepository    = courierRepository;
	}

	public void createRestaurant(long restaurantId, String restaurantName, Address address) {
		restaurantRepository.save(Restaurant.create(restaurantId, restaurantName, address));
	}

	public void createDelivery(long orderId, long restaurantId, Address deliveryAddress) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
		deliveryRepository.save(Delivery.create(orderId, restaurantId, restaurant.getAddress(), deliveryAddress));
	}

	public void scheduleDelivery(long orderId, LocalDateTime readyBy) {
		Delivery delivery = deliveryRepository.findById(orderId).get();
		delivery.schedule(readyBy);

		List<Courier> couriers = courierRepository.findAllAvailable();
		Courier courier = couriers.get(random.nextInt(couriers.size()));
		delivery.assignCourier(courier.getId());
		courier.addAction(Action.makePickup(delivery.getId(), delivery.getPickupAddress(), readyBy));
		courier.addAction(Action.makeDropoff(delivery.getId(), delivery.getDeliveryAddress(), readyBy.plusMinutes(30)));
	}

	public void cancelDelivery(long orderId) {
		Delivery delivery = deliveryRepository.findById(orderId).get();
		Long assignedCourierId = delivery.getAssignedCourier();
		delivery.cancel();
		if (assignedCourierId != null) {
			Courier courier = courierRepository.findById(assignedCourierId).get();
			courier.cancelDelivery(delivery.getId());
		}

	}

	void noteAvailable(long courierId) {
		courierRepository.findOrCreateCourier(courierId).noteAvailable();
	}

	void noteUnavailable(long courierId) {
		courierRepository.findOrCreateCourier(courierId).noteUnavailable();
	}

	private Courier findOrCreateCourier(long courierId) {
		Courier courier = Courier.create(courierId);
		try {
			return courierRepository.save(courier);
		} catch (DuplicateKeyException e) {
			return courierRepository.findById(courierId).get();
		}
	}

	@Transactional
	public void updateAvailability(long courierId, boolean available) {
		if (available)
			noteAvailable(courierId);
		else
			noteUnavailable(courierId);
	}

	@Transactional
	public DeliveryStatus getDeliveryInfo(long deliveryId) {
		Optional<Delivery> deliveryOpt = deliveryRepository.findById(deliveryId);
		if (!deliveryOpt.isPresent()) {
			return null;
		}
		Delivery delivery = deliveryOpt.get();
		Long assignedCourier = delivery.getAssignedCourier();
		List<Action> courierActions = Collections.emptyList();
		if (assignedCourier != null) {
			Courier courier = courierRepository.findById(assignedCourier).get();
			courierActions = courier.actionsForDelivery(deliveryId);
		}
		return makeDeliveryStatus(delivery, assignedCourier, courierActions);
	}

	private DeliveryStatus makeDeliveryStatus(Delivery delivery, Long assignedCourier, List<Action> courierActions) {
		return new DeliveryStatus(makeDeliveryInfo(delivery), assignedCourier,
				courierActions.stream().map(action -> makeActionInfo(action)).collect(Collectors.toList()));
	}

	private DeliveryInfo makeDeliveryInfo(Delivery delivery) {
		return new DeliveryInfo(delivery.getId(), delivery.getState().name());
	}

	private ActionInfo makeActionInfo(Action action) {
		return new ActionInfo(action.getType().name());
	}
}
