package com.ftgo.orderservice.command;

import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.domain.CommonConfiguration;

@Configuration
@Import({ SagaParticipantConfiguration.class, TramEventsPublisherConfiguration.class, CommonConfiguration.class })
public class OrderServiceCommandHandlersConfiguration {
	@Bean
	public OrderServiceCommandHandlers orderCommandHandlers() {
		return new OrderServiceCommandHandlers();
	}

	@Bean
	public SagaCommandDispatcher orderCommandHandlersDispatcher(OrderServiceCommandHandlers orderCommandHandlers) {
		return new SagaCommandDispatcher("orderService", orderCommandHandlers.commandHandlers());
	}
}
