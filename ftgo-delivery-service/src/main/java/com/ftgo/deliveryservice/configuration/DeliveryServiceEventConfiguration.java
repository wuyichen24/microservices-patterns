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
package com.ftgo.deliveryservice.configuration;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.configuration.CommonConfiguration;
import com.ftgo.deliveryservice.event.DeliveryServiceEventConsumer;
import com.ftgo.deliveryservice.service.DeliveryService;

/**
 * The configuration class to instantiate and wire the event consumer and publisher.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@Import({ DeliveryServiceConfiguration.class, TramEventSubscriberConfiguration.class, CommonConfiguration.class })
public class DeliveryServiceEventConfiguration {
	@Bean
	public DeliveryServiceEventConsumer deliveryMessageHandlers(DeliveryService deliveryService) {
		return new DeliveryServiceEventConsumer(deliveryService);
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(DeliveryServiceEventConsumer deliveryMessageHandlers, DomainEventDispatcherFactory domainEventDispatcherFactory) {
		return domainEventDispatcherFactory.make("deliveryServiceEvents", deliveryMessageHandlers.domainEventHandlers());
	}
}
