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
package com.ftgo.orderservice.service;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaManager;

import org.junit.Before;
import org.junit.Test;

import com.ftgo.orderservice.OrderTestData;
import com.ftgo.orderservice.RestaurantTestData;
import com.ftgo.orderservice.api.event.OrderCreatedEvent;
import com.ftgo.orderservice.event.OrderServiceEventPublisher;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.repository.RestaurantRepository;
import com.ftgo.orderservice.saga.cancelorder.CancelOrderSagaData;
import com.ftgo.orderservice.saga.createorder.CreateOrderSagaData;
import com.ftgo.orderservice.saga.reviseorder.ReviseOrderSagaData;
import com.ftgo.orderservice.service.OrderService;

import java.util.Collections;
import java.util.Optional;

import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_MENU_ITEMS_AND_QUANTITIES;
import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_ORDER_DETAILS;
import static com.ftgo.orderservice.OrderTestData.CONSUMER_ID;
import static com.ftgo.orderservice.OrderTestData.ORDER_ID;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_RESTAURANT;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for order service.
 * 
 * <p>This unit test uses Mockito mocks.
 *
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderServiceTest {
	private OrderService                     orderService;
	private OrderRepository                  orderRepository;
	private DomainEventPublisher             eventPublisher;
	private RestaurantRepository             restaurantRepository;
	private SagaManager<CreateOrderSagaData> createOrderSagaManager;
	private SagaManager<CancelOrderSagaData> cancelOrderSagaManager;
	private SagaManager<ReviseOrderSagaData> reviseOrderSagaManager;
	private OrderServiceEventPublisher        orderAggregateEventPublisher;

	@Before
	public void setup() {
		orderRepository        = mock(OrderRepository.class);
		eventPublisher         = mock(DomainEventPublisher.class);
		restaurantRepository   = mock(RestaurantRepository.class);
		createOrderSagaManager = mock(SagaManager.class);
		cancelOrderSagaManager = mock(SagaManager.class);
		reviseOrderSagaManager = mock(SagaManager.class);

		// Mock DomainEventPublisher AND use the real OrderDomainEventPublisher

		orderAggregateEventPublisher = mock(OrderServiceEventPublisher.class);

		orderService = new OrderService(orderRepository, eventPublisher,
				restaurantRepository, createOrderSagaManager,
				cancelOrderSagaManager, reviseOrderSagaManager,
				orderAggregateEventPublisher, Optional.empty());
	}

	@Test
	public void shouldCreateOrder() {
		when(restaurantRepository.findById(AJANTA_ID)).thenReturn(Optional.of(AJANTA_RESTAURANT));
		when(orderRepository.save(any(Order.class))).then(invocation -> {
			Order order = (Order) invocation.getArguments()[0];
			order.setId(ORDER_ID);
			return order;
		});

		Order order = orderService.createOrder(CONSUMER_ID, AJANTA_ID, OrderTestData.DELIVERY_INFORMATION, CHICKEN_VINDALOO_MENU_ITEMS_AND_QUANTITIES);

		verify(orderRepository).save(same(order));

		verify(orderAggregateEventPublisher).publish(order, Collections.singletonList(new OrderCreatedEvent(CHICKEN_VINDALOO_ORDER_DETAILS, OrderTestData.DELIVERY_ADDRESS, RestaurantTestData.AJANTA_RESTAURANT_NAME)));

		verify(createOrderSagaManager).create(new CreateOrderSagaData(ORDER_ID, CHICKEN_VINDALOO_ORDER_DETAILS), Order.class, ORDER_ID);
	}
}