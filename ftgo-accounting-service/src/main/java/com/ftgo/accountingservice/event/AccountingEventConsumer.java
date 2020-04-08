package com.ftgo.accountingservice.event;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.accountingservice.service.AccountingService;
import com.ftgo.consumerservice.event.model.ConsumerCreatedEvent;

public class AccountingEventConsumer {
	@Autowired
	private AccountingService accountingService;

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder.forAggregateType("com.ftgo.consumerservice.model.Consumer")
				.onEvent(ConsumerCreatedEvent.class, this::createAccount) // TODO this is hack to get the correct package
				.build();
	}

	private void createAccount(DomainEventEnvelope<ConsumerCreatedEvent> dee) {
		accountingService.create(dee.getAggregateId());
	}
}
