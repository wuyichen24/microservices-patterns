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
package com.ftgo.orderhistoryservice.event;

import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.orderhistoryservice.configuration.OrderHistoryServiceEventConfiguration;
import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.dao.dynamodb.SourceEvent;
import com.ftgo.orderhistoryservice.model.Order;

import java.util.Optional;

import static io.eventuate.util.test.async.Eventually.eventually;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The integration test for the event handler of the order history service.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderHistoryEventHandlersTest.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = { "com.ftgo:ftgo-order-service-contracts" })
@DirtiesContext
public class OrderHistoryEventHandlersTest {
	@Configuration
	@EnableAutoConfiguration
	@Import({ OrderHistoryServiceEventConfiguration.class, TramCommandProducerConfiguration.class, TramInMemoryConfiguration.class, EventuateContractVerifierConfiguration.class })
	public static class TestConfiguration {
		@Bean
		public ChannelMapping channelMapping() {
			return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
		}

		@Bean
		public OrderHistoryDao orderHistoryDao() {
			return mock(OrderHistoryDao.class);
		}
	}

	@Autowired
	private StubFinder stubFinder;

	@Autowired
	private OrderHistoryDao orderHistoryDao;

	@Test
	public void shouldHandleOrderCreatedEvent() throws InterruptedException {
		when(orderHistoryDao.addOrder(any(Order.class), any(Optional.class))).thenReturn(false);

		stubFinder.trigger("orderCreatedEvent");

		eventually(() -> {
			ArgumentCaptor<Order> orderArg = ArgumentCaptor.forClass(Order.class);
			ArgumentCaptor<Optional<SourceEvent>> sourceEventArg = ArgumentCaptor.forClass(Optional.class);
			verify(orderHistoryDao).addOrder(orderArg.capture(), sourceEventArg.capture());

			Order order = orderArg.getValue();
			Optional<SourceEvent> sourceEvent = sourceEventArg.getValue();

			assertEquals("Ajanta", order.getRestaurantName());
		});
	}
}
