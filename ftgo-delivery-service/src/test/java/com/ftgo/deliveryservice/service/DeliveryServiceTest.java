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

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ftgo.deliveryservice.model.Action;
import com.ftgo.deliveryservice.model.ActionType;
import com.ftgo.deliveryservice.model.Courier;
import com.ftgo.deliveryservice.model.Delivery;
import com.ftgo.deliveryservice.model.DeliveryState;
import com.ftgo.deliveryservice.model.Restaurant;
import com.ftgo.deliveryservice.repository.CourierRepository;
import com.ftgo.deliveryservice.repository.DeliveryRepository;
import com.ftgo.deliveryservice.repository.RestaurantRepository;
import com.ftgo.deliveryservice.service.DeliveryService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * The test class for the delivery service.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class DeliveryServiceTest {
	private static final long          COURIER_ID    = 101L;
	private static final long          ORDER_ID      = 102L;
	private static final long          RESTAURANT_ID = 103L;
	private static final LocalDateTime READY_BY      = LocalDateTime.now();

	private RestaurantRepository restaurantRepository;
	private DeliveryRepository   deliveryRepository;
	private CourierRepository    courierRepository;
	private DeliveryService      deliveryService;
	
	private Restaurant           restaurant;
	private Courier              courier;

	@Before
	public void setUp() {
		this.restaurantRepository = mock(RestaurantRepository.class);
		this.deliveryRepository   = mock(DeliveryRepository.class);
		this.courierRepository    = mock(CourierRepository.class);
		this.courier              = Courier.create(COURIER_ID);
		this.restaurant           = mock(Restaurant.class);

		this.deliveryService = new DeliveryService(restaurantRepository, deliveryRepository, courierRepository);
	}

	@Test
	public void shouldCreateCourier() {
		when(courierRepository.findOrCreateCourier(COURIER_ID)).thenReturn(courier);
		deliveryService.noteAvailable(COURIER_ID);
		assertTrue(courier.isAvailable());
	}

	@Test
	public void shouldCreateDelivery() {
		when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
		when(restaurant.getAddress()).thenReturn(DeliveryServiceTestData.PICKUP_ADDRESS);
		deliveryService.createDelivery(ORDER_ID, RESTAURANT_ID, DeliveryServiceTestData.DELIVERY_ADDRESS);

		ArgumentCaptor<Delivery> arg = ArgumentCaptor.forClass(Delivery.class);
		verify(deliveryRepository).save(arg.capture());

		Delivery delivery = arg.getValue();
		assertNotNull(delivery);

		assertEquals(ORDER_ID, delivery.getId());
		assertEquals(DeliveryState.PENDING, delivery.getState());
		assertEquals(RESTAURANT_ID, delivery.getRestaurantId());
		assertEquals(DeliveryServiceTestData.PICKUP_ADDRESS, delivery.getPickupAddress());
		assertEquals(DeliveryServiceTestData.DELIVERY_ADDRESS, delivery.getDeliveryAddress());
	}

	@Test
	public void shouldScheduleDelivery() {
		Delivery delivery = Delivery.create(ORDER_ID, RESTAURANT_ID, DeliveryServiceTestData.PICKUP_ADDRESS, DeliveryServiceTestData.DELIVERY_ADDRESS);

		when(deliveryRepository.findById(ORDER_ID)).thenReturn(Optional.of(delivery));
		when(courierRepository.findAllAvailable()).thenReturn(Collections.singletonList(courier));

		deliveryService.scheduleDelivery(ORDER_ID, READY_BY);

		assertSame(courier.getId(), delivery.getAssignedCourier());
		List<Action> actions = courier.getPlan().getActions();
		assertEquals(2, actions.size());
		assertEquals(ActionType.PICKUP, actions.get(0).getType());
		assertEquals(DeliveryServiceTestData.PICKUP_ADDRESS, actions.get(0).getAddress());
		assertEquals(ActionType.DROPOFF, actions.get(1).getType());
		assertEquals(DeliveryServiceTestData.DELIVERY_ADDRESS, actions.get(1).getAddress());
	}
}