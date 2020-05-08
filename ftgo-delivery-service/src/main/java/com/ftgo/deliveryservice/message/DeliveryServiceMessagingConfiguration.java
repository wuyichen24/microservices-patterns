package com.ftgo.deliveryservice.message;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.domain.CommonConfiguration;
import com.ftgo.deliveryservice.domain.DeliveryServiceDomainConfiguration;
import com.ftgo.deliveryservice.event.DeliveryServiceEventConsumer;
import com.ftgo.deliveryservice.service.DeliveryService;

@Configuration
@Import({ DeliveryServiceDomainConfiguration.class, TramEventSubscriberConfiguration.class, CommonConfiguration.class })
public class DeliveryServiceMessagingConfiguration {
	@Bean
	public DeliveryServiceEventConsumer deliveryMessageHandlers(DeliveryService deliveryService) {
		return new DeliveryServiceEventConsumer(deliveryService);
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(DeliveryServiceEventConsumer deliveryMessageHandlers, DomainEventDispatcherFactory domainEventDispatcherFactory) {
		return domainEventDispatcherFactory.make("deliveryService-deliveryMessageHandlers", deliveryMessageHandlers.domainEventHandlers());
	}
}
