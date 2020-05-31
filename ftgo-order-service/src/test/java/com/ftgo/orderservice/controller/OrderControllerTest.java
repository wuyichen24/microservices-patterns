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
package com.ftgo.orderservice.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.orderservice.OrderTestData;
import com.ftgo.orderservice.controller.OrderServiceController;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.service.OrderService;

import io.eventuate.common.json.mapper.JSonMapper;

import java.util.Optional;

import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_ORDER;
import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_ORDER_TOTAL;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for the REST controller of the order service.
 * 
 * <p>This unit test uses Spring Mock MVC framework and Mockito mocks.
 *
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderControllerTest {
	private OrderService    orderService;
	private OrderRepository orderRepository;
	private OrderServiceController orderController;

	@Before
	public void setUp() throws Exception {
		orderService    = mock(OrderService.class);
		orderRepository = mock(OrderRepository.class);
		orderController = new OrderServiceController(orderService, orderRepository);
	}

	@Test
	public void shouldFindOrder() {
		when(orderRepository.findById(1L)).thenReturn(Optional.of(CHICKEN_VINDALOO_ORDER));

		given().standaloneSetup(configureControllers(orderController))
				.when()
				.get("/orders/1")
				.then()
				.statusCode(200)
				.body("orderId",    equalTo(new Long(OrderTestData.ORDER_ID).intValue()))
				.body("state",      equalTo(OrderTestData.CHICKEN_VINDALOO_ORDER_STATE.name()))
				.body("orderTotal", equalTo(CHICKEN_VINDALOO_ORDER_TOTAL.asString()));
	}

	@Test
	public void shouldFindNotOrder() {
		when(orderRepository.findById(1L)).thenReturn(Optional.empty());

		given().standaloneSetup(configureControllers(new OrderServiceController(orderService, orderRepository))).when().get("/orders/1").then()
				.statusCode(404);
	}

	private StandaloneMockMvcBuilder configureControllers(Object... controllers) {
		CommonJsonMapperInitializer.registerMoneyModule();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(JSonMapper.objectMapper);
		return MockMvcBuilders
				.standaloneSetup(controllers)
				.setMessageConverters(converter);
	}
}