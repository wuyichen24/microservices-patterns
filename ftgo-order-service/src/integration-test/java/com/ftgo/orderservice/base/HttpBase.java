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
package com.ftgo.orderservice.base;

import io.eventuate.common.json.mapper.JSonMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.Before;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.orderservice.OrderTestData;
import com.ftgo.orderservice.controller.OrderServiceController;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.service.OrderService;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The base class for the setup phase of the HTTP server test.
 * 
 * <p>It creates the controllers injected with mock dependencies and 
 * configures those mocks to return values that cause the controller 
 * to generate the expected response.
 *
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
public abstract class HttpBase {
	private StandaloneMockMvcBuilder controllers(Object... controllers) {
		CommonJsonMapperInitializer.registerMoneyModule();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(JSonMapper.objectMapper);
		return MockMvcBuilders.standaloneSetup(controllers).setMessageConverters(converter);
	}

	@Before
	public void setup() {
		OrderService orderService = mock(OrderService.class);
		OrderRepository orderRepository = mock(OrderRepository.class);
		OrderServiceController orderController = new OrderServiceController(orderService, orderRepository);

		when(orderRepository.findById(OrderTestData.ORDER_ID)).thenReturn(Optional.of(OrderTestData.CHICKEN_VINDALOO_ORDER));
		when(orderRepository.findById(555L)).thenReturn(empty());
		RestAssuredMockMvc.standaloneSetup(controllers(orderController));
	}
}