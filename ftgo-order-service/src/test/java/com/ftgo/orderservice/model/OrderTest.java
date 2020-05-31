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
package com.ftgo.orderservice.model;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;

import org.junit.Before;
import org.junit.Test;

import com.ftgo.orderservice.OrderTestData;
import com.ftgo.orderservice.RestaurantTestData;
import com.ftgo.orderservice.api.event.OrderCreatedEvent;
import com.ftgo.orderservice.api.event.OrderDomainEvent;
import com.ftgo.orderservice.api.model.OrderState;
import com.ftgo.orderservice.controller.model.OrderRevision;
import com.ftgo.orderservice.event.model.OrderAuthorizedEvent;
import com.ftgo.orderservice.model.LineItemQuantityChange;
import com.ftgo.orderservice.model.Order;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ftgo.orderservice.OrderTestData.*;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_RESTAURANT;
import static com.ftgo.orderservice.RestaurantTestData.CHICKEN_VINDALOO_PRICE;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for Order entity.
 *
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderTest {
	private ResultWithDomainEvents<Order, OrderDomainEvent> createResult;
	private Order                                           order;

	@Before
	public void setUp() throws Exception {
		createResult = Order.createOrder(CONSUMER_ID, AJANTA_RESTAURANT, OrderTestData.DELIVERY_INFORMATION, chickenVindalooLineItems());
		order        = createResult.result;
	}

	@Test
	public void shouldCreateOrder() {
		assertEquals(singletonList(new OrderCreatedEvent(CHICKEN_VINDALOO_ORDER_DETAILS, OrderTestData.DELIVERY_ADDRESS, RestaurantTestData.AJANTA_RESTAURANT_NAME)), createResult.events);

		assertEquals(OrderState.APPROVAL_PENDING, order.getState());
	}

	@Test
	public void shouldCalculateTotal() {
		assertEquals(
				CHICKEN_VINDALOO_PRICE.multiply(CHICKEN_VINDALOO_QUANTITY),
				order.getOrderTotal());
	}

	@Test
	public void shouldAuthorize() {
		List<OrderDomainEvent> events = order.noteApproved();
		assertEquals(singletonList(new OrderAuthorizedEvent()), events);
		assertEquals(OrderState.APPROVED, order.getState());
	}

	@Test
	public void shouldReviseOrder() {
		order.noteApproved();
		OrderRevision orderRevision = new OrderRevision(Optional.empty(), Collections.singletonMap("1", 10));
		ResultWithDomainEvents<LineItemQuantityChange, OrderDomainEvent> result = order.revise(orderRevision);
		assertEquals(CHICKEN_VINDALOO_PRICE.multiply(10), result.result.getNewOrderTotal());
		order.confirmRevision(orderRevision);
		assertEquals(CHICKEN_VINDALOO_PRICE.multiply(10), order.getOrderTotal());
	}
}