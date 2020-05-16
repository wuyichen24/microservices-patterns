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
