package com.ftgo.kitchenservice.message;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.domain.CommonConfiguration;
import com.ftgo.kitchenservice.command.KitchenServiceCommandHandler;
import com.ftgo.kitchenservice.domain.KitchenDomainConfiguration;
import com.ftgo.kitchenservice.event.KitchenServiceEventConsumer;

@Configuration
@Import({ KitchenDomainConfiguration.class, SagaParticipantConfiguration.class, CommonConfiguration.class })
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
	public SagaCommandDispatcher kitchenServiceSagaCommandDispatcher(KitchenServiceCommandHandler kitchenServiceCommandHandler) {
		return new SagaCommandDispatcher("kitchenServiceCommands", kitchenServiceCommandHandler.commandHandlers());
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(KitchenServiceEventConsumer kitchenServiceEventConsumer, MessageConsumer messageConsumer) {
		return new DomainEventDispatcher("kitchenServiceEvents", kitchenServiceEventConsumer.domainEventHandlers(), messageConsumer); // @Autowire
	}
}
