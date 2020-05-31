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
package com.ftgo.orderhistoryservice.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.orderhistoryservice.controller.OrderHistoryServiceController;
import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.model.Order;

import io.eventuate.common.json.mapper.JSonMapper;

import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderHistoryControllerTest {
	private OrderHistoryDao orderHistoryDao;
	private OrderHistoryServiceController orderHistoryController;

	@Before
	public void setUp() {
		orderHistoryDao = mock(OrderHistoryDao.class);
		orderHistoryController = new OrderHistoryServiceController(orderHistoryDao);
	}

	@Test
	public void testGetOrder() {
		when(orderHistoryDao.findOrder("1"))
				.thenReturn(Optional.of(new Order("1", null, null, null, null, 101L, "Ajanta")));

		given().standaloneSetup(configureControllers(orderHistoryController)).when().get("/orders/1").then()
				.statusCode(200).body("restaurantName", equalTo("Ajanta"));
	}

	private StandaloneMockMvcBuilder configureControllers(Object... controllers) {
		CommonJsonMapperInitializer.registerMoneyModule();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(JSonMapper.objectMapper);
		return MockMvcBuilders.standaloneSetup(controllers).setMessageConverters(converter);
	}
}