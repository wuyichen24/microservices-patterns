package com.ftgo.accountingservice.messaging;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.accountingservice.service.AccountingService;
import com.ftgo.consumerservice.domain.ConsumerCreated;

public class AccountingEventConsumer {
	@Autowired
	private AccountingService accountingService;

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder.forAggregateType("net.chrisrichardson.ftgo.consumerservice.domain.Consumer")
				.onEvent(ConsumerCreated.class, this::createAccount) // TODO this is hack to get the correct package
				.build();
	}

	private void createAccount(DomainEventEnvelope<ConsumerCreated> dee) {
		accountingService.create(dee.getAggregateId());
	}
}
