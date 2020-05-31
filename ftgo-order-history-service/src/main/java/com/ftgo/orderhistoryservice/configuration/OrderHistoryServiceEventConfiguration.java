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
package com.ftgo.orderhistoryservice.configuration;

import io.eventuate.tram.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.configuration.CommonConfiguration;
import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.event.OrderHistoryEventConsumer;

/**
 * The configuration class to instantiate and wire the event consumer and publisher.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@Import({ CommonConfiguration.class, TramNoopDuplicateMessageDetectorConfiguration.class, DomainEventDispatcherFactory.class })
public class OrderHistoryServiceEventConfiguration {
	@Bean
	public OrderHistoryEventConsumer orderHistoryEventHandlers(OrderHistoryDao orderHistoryDao) {
		return new OrderHistoryEventConsumer(orderHistoryDao);
	}

	@Bean
	public DomainEventDispatcher orderHistoryDomainEventDispatcher(OrderHistoryEventConsumer orderHistoryEventHandlers, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("orderHistoryDomainEventDispatcher", orderHistoryEventHandlers.domainEventHandlers());
	}
}
