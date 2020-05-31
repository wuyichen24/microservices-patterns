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

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.orderservice.OrderTestData;
import com.ftgo.orderservice.api.event.OrderCreatedEvent;
import com.ftgo.orderservice.event.OrderServiceEventPublisher;

import java.util.Collections;

import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_ORDER;
import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_ORDER_DETAILS;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_RESTAURANT_NAME;

/**
 * The base class for the setup phase of the messaging test.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessagingBase.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class MessagingBase {
	@Configuration
	@EnableAutoConfiguration
	@Import({ EventuateContractVerifierConfiguration.class, TramEventsPublisherConfiguration.class, TramInMemoryConfiguration.class })
	public static class TestConfiguration {
		@Bean
		public ChannelMapping channelMapping() {
			return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
		}

		@Bean
		public OrderServiceEventPublisher orderAggregateEventPublisher(DomainEventPublisher eventPublisher) {
			return new OrderServiceEventPublisher(eventPublisher);
		}
	}

	@Autowired
	private OrderServiceEventPublisher orderAggregateEventPublisher;

	protected void orderCreated() {
	    orderAggregateEventPublisher.publish(CHICKEN_VINDALOO_ORDER, Collections.singletonList(new OrderCreatedEvent(CHICKEN_VINDALOO_ORDER_DETAILS, OrderTestData.DELIVERY_ADDRESS, AJANTA_RESTAURANT_NAME)));
	}
}
