package com.ftgo.accountingservice.message;

import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
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
@Import({ AccountingServiceConfiguration.class, CommonConfiguration.class })
public class AccountingServiceMessageConfiguration {
	@Bean
	public AccountingEventConsumer accountingEventConsumer() {
		return new AccountingEventConsumer();
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(AccountingEventConsumer accountingEventConsumer,
			MessageConsumer messageConsumer) {
		return new DomainEventDispatcher("accountingServiceDomainEventDispatcher",
				accountingEventConsumer.domainEventHandlers(), messageConsumer);
	}

	@Bean
	public AccountingServiceCommandHandler accountCommandHandler() {
		return new AccountingServiceCommandHandler();
	}

	@Bean
	public CommandDispatcher commandDispatcher(AccountingServiceCommandHandler target,
			AccountingServiceChannelConfiguration data) {
		return new CommandDispatcher(data.getCommandDispatcherId(), target.commandHandlers());
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
		return new SagaReplyRequestedEventSubscriber("accountingServiceSagaReplyRequestedEventSubscriber",
				Collections.singleton(Account.class.getName()));
	}
}
