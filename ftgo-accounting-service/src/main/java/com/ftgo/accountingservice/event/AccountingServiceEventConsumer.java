package com.ftgo.accountingservice.event;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.accountingservice.service.AccountingService;
import com.ftgo.consumerservice.api.event.model.ConsumerCreatedEvent;


/**
 * The event handlers for incoming events.
 *
 * @author  Wuyi Chen
 * @date    04/14/2020
 * @version 1.0
 * @since   1.0
 */
public class AccountingServiceEventConsumer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AccountingService accountingService;

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder.forAggregateType("com.ftgo.consumerservice.model.Consumer")    // it will create a new Kafka topic: com.ftgo.consumerservice.model.Consumer
				.onEvent(ConsumerCreatedEvent.class, this::createAccount) // TODO this is hack to get the correct package
				.build();
	}

	private void createAccount(DomainEventEnvelope<ConsumerCreatedEvent> dee) {
		logger.debug("Receive ConsumerCreatedEvent");
		accountingService.create(dee.getAggregateId());
	}
}
