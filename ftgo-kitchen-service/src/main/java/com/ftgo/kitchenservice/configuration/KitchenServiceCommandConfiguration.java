package com.ftgo.kitchenservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.configuration.CommonConfiguration;
import com.ftgo.kitchenservice.command.KitchenServiceCommandHandler;

import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

/**
 * The configuration class to instantiate and wire the command handler.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@Import({ KitchenServiceConfiguration.class, SagaParticipantConfiguration.class, CommonConfiguration.class, DomainEventDispatcherFactory.class, SagaCommandDispatcherFactory.class })
public class KitchenServiceCommandConfiguration {
	@Bean
	public KitchenServiceCommandHandler kitchenServiceCommandHandler() {
		return new KitchenServiceCommandHandler();
	}
	
	@Bean
	public SagaCommandDispatcher kitchenServiceSagaCommandDispatcher(KitchenServiceCommandHandler kitchenServiceCommandHandler, SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
	    return sagaCommandDispatcherFactory.make("kitchenServiceCommands", kitchenServiceCommandHandler.commandHandlers());
	}
}
