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
import com.ftgo.common.domain.CommonConfiguration;

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
