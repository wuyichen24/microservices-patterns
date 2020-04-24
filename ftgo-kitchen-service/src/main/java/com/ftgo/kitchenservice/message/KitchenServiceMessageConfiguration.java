package com.ftgo.kitchenservice.message;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.domain.CommonConfiguration;
import com.ftgo.kitchenservice.command.KitchenServiceCommandHandler;
import com.ftgo.kitchenservice.domain.KitchenDomainConfiguration;
import com.ftgo.kitchenservice.event.KitchenServiceEventConsumer;

@Configuration
@Import({ KitchenDomainConfiguration.class, SagaParticipantConfiguration.class, CommonConfiguration.class, DomainEventDispatcherFactory.class, SagaCommandDispatcherFactory.class })
public class KitchenServiceMessageConfiguration {
	@Bean
	public KitchenServiceEventConsumer ticketEventConsumer() {
		return new KitchenServiceEventConsumer();
	}

	@Bean
	public KitchenServiceCommandHandler kitchenServiceCommandHandler() {
		return new KitchenServiceCommandHandler();
	}

	@Bean
	public SagaCommandDispatcher kitchenServiceSagaCommandDispatcher(KitchenServiceCommandHandler kitchenServiceCommandHandler, SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
	    return sagaCommandDispatcherFactory.make("kitchenServiceCommands", kitchenServiceCommandHandler.commandHandlers());
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(KitchenServiceEventConsumer kitchenServiceEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("kitchenServiceEvents", kitchenServiceEventConsumer.domainEventHandlers());
	}
}
