/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
