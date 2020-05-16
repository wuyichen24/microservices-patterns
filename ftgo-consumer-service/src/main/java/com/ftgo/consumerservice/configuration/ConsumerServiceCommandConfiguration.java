package com.ftgo.consumerservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ftgo.consumerservice.command.ConsumerServiceCommandHandlers;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;

/**
 * The configuration class to instantiate and wire the command handler.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
public class ConsumerServiceCommandConfiguration {
	@Bean
	public ConsumerServiceCommandHandlers consumerServiceCommandHandlers() {
		return new ConsumerServiceCommandHandlers();
	}

	@Bean
	public CommandDispatcher commandDispatcher(ConsumerServiceCommandHandlers consumerServiceCommandHandlers, SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
		return sagaCommandDispatcherFactory.make("consumerServiceDispatcher", consumerServiceCommandHandlers.commandHandlers());
	}
}
