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
package com.ftgo.orderservice.event;

import org.junit.Before;
import org.junit.Test;

import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.orderservice.RestaurantTestData;
import com.ftgo.orderservice.event.OrderServiceEventConsumer;
import com.ftgo.orderservice.service.OrderService;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import static com.ftgo.orderservice.RestaurantTestData.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_RESTAURANT_NAME;
import static io.eventuate.tram.testing.DomainEventHandlerUnitTestSupport.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit test for the event consumer of the order service.
 * 
 * <p>This unit test uses Eventuate Tram Mock Messaging framework and Mockito mocks.
 *
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderEventConsumerTest {
	private OrderService              orderService;
	private OrderServiceEventConsumer orderEventConsumer;

	@Before
	public void setUp() throws Exception {
		orderService       = mock(OrderService.class);
		orderEventConsumer = new OrderServiceEventConsumer(orderService);
	}

	@Test
	public void shouldCreateMenu() {
		CommonJsonMapperInitializer.registerMoneyModule();

		given().eventHandlers(orderEventConsumer.domainEventHandlers())
				.when()
				.aggregate("com.ftgo.orderservice.model.Restaurant", AJANTA_ID)
				.publishes(new RestaurantCreatedEvent(AJANTA_RESTAURANT_NAME, RestaurantTestData.RESTAURANT_ADDRESS, RestaurantTestData.AJANTA_RESTAURANT_MENU))
				.then()
				.verify(() -> {
					verify(orderService)
							.createMenu(
									AJANTA_ID,
									AJANTA_RESTAURANT_NAME,
									new RestaurantMenu(RestaurantTestData.AJANTA_RESTAURANT_MENU_ITEMS));
				});

	}
}