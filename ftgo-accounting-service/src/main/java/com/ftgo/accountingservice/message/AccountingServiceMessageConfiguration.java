package com.ftgo.accountingservice.message;

import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.commands.consumer.CommandDispatcherFactory;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.sagas.eventsourcingsupport.SagaReplyRequestedEventSubscriber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.command.AccountingServiceCommandHandler;
import com.ftgo.accountingservice.event.AccountingEventConsumer;
import com.ftgo.accountingservice.model.Account;
import com.ftgo.accountingservice.service.AccountingServiceConfiguration;
import com.ftgo.common.domain.CommonConfiguration;

import java.util.Collections;

@Configuration
@EnableEventHandlers
@Import({ AccountingServiceConfiguration.class, CommonConfiguration.class, DomainEventDispatcherFactory.class, CommandDispatcherFactory.class })
public class AccountingServiceMessageConfiguration {
	@Bean
	public AccountingEventConsumer accountingEventConsumer() {
		return new AccountingEventConsumer();
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(AccountingEventConsumer accountingEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
		return domainEventDispatcherFactory.make("accountingServiceDomainEventDispatcher", accountingEventConsumer.domainEventHandlers());
	}

	@Bean
	public AccountingServiceCommandHandler accountCommandHandler() {
		return new AccountingServiceCommandHandler();
	}

	@Bean
	public CommandDispatcher commandDispatcher(AccountingServiceCommandHandler target, AccountingServiceChannelConfiguration data, CommandDispatcherFactory commandDispatcherFactory) {
		return commandDispatcherFactory.make(data.getCommandDispatcherId(), target.commandHandlers());
	}

	@Bean
	public DuplicateMessageDetector duplicateMessageDetector() {
		return new NoopDuplicateMessageDetector();
	}

	@Bean
	public AccountingServiceChannelConfiguration accountServiceChannelConfiguration() {
		return new AccountingServiceChannelConfiguration("accountCommandDispatcher", "accountCommandChannel");
	}

	@Bean
	public SagaReplyRequestedEventSubscriber sagaReplyRequestedEventSubscriber() {
		return new SagaReplyRequestedEventSubscriber("accountingServiceSagaReplyRequestedEventSubscriber", Collections.singleton(Account.class.getName()));
	}

}
