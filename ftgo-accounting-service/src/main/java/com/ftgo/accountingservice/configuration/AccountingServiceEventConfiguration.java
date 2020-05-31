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
package com.ftgo.accountingservice.configuration;

import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.sagas.eventsourcingsupport.SagaReplyRequestedEventSubscriber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.event.AccountingServiceEventConsumer;
import com.ftgo.accountingservice.message.NoopDuplicateMessageDetector;
import com.ftgo.accountservice.api.AccountingServiceChannels;
import com.ftgo.common.configuration.CommonConfiguration;

import java.util.Collections;

/**
 * The configuration class to instantiate and wire the event consumer and publisher.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableEventHandlers
@Import({ AccountingServiceConfiguration.class, CommonConfiguration.class, DomainEventDispatcherFactory.class })
public class AccountingServiceEventConfiguration {
	@Bean
	public AccountingServiceEventConsumer accountingEventConsumer() {
		return new AccountingServiceEventConsumer();
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(AccountingServiceEventConsumer accountingEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
		return domainEventDispatcherFactory.make("accountingServiceDomainEventDispatcher", accountingEventConsumer.domainEventHandlers());
	}

	@Bean
	public DuplicateMessageDetector duplicateMessageDetector() {
		return new NoopDuplicateMessageDetector();
	}

	@Bean
	public SagaReplyRequestedEventSubscriber sagaReplyRequestedEventSubscriber() {
		return new SagaReplyRequestedEventSubscriber("accountingServiceSagaReplyRequestedEventSubscriber", Collections.singleton(AccountingServiceChannels.ACCOUNT_EVENT_CHANNEL));  // it will create a new Kafka topic: com.ftgo.accountingservice.model.Account
	}
}
