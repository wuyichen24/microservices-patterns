package com.ftgo.kitchenservice.configuration;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.configuration.CommonConfiguration;
import com.ftgo.kitchenservice.event.KitchenServiceEventConsumer;
import com.ftgo.kitchenservice.event.TicketDomainEventPublisher;

/**
 * The configuration class to instantiate and wire the event consumer and publisher.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@Import({ KitchenServiceConfiguration.class, SagaParticipantConfiguration.class, CommonConfiguration.class, DomainEventDispatcherFactory.class, SagaCommandDispatcherFactory.class })
public class KitchenServiceEventConfiguration {
	@Bean
	public TicketDomainEventPublisher restaurantAggregateEventPublisher(DomainEventPublisher domainEventPublisher) {
		return new TicketDomainEventPublisher(domainEventPublisher);
	}
	
	@Bean
	public KitchenServiceEventConsumer ticketEventConsumer() {
		return new KitchenServiceEventConsumer();
	}
	
	@Bean
	public DomainEventDispatcher domainEventDispatcher(KitchenServiceEventConsumer kitchenServiceEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("kitchenServiceEvents", kitchenServiceEventConsumer.domainEventHandlers());
	}
}
