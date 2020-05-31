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
package com.ftgo.orderservice.configuration;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.orderservice.event.OrderServiceEventConsumer;
import com.ftgo.orderservice.event.OrderServiceEventPublisher;
import com.ftgo.orderservice.service.OrderService;

/**
 * The configuration class to instantiate and wire the event consumer and publisher.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@Import({ OrderServiceRepositoriesConfiguration.class, DomainEventDispatcherFactory.class })
public class OrderServiceEventConfiguration {
	@Bean
	public OrderServiceEventPublisher orderAggregateEventPublisher(DomainEventPublisher eventPublisher) {
		return new OrderServiceEventPublisher(eventPublisher);
	}
	
	@Bean
	public OrderServiceEventConsumer orderEventConsumer(OrderService orderService) {
		return new OrderServiceEventConsumer(orderService);
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(OrderServiceEventConsumer orderEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("orderServiceEvents", orderEventConsumer.domainEventHandlers());
	}
}
