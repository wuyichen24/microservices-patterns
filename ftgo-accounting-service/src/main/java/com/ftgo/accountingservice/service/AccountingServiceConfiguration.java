package com.ftgo.accountingservice.service;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.command.AccountCommand;
import com.ftgo.accountingservice.model.Account;
import com.ftgo.common.domain.CommonConfiguration;

@Configuration
@Import({ TramCommandProducerConfiguration.class, CommonConfiguration.class })
public class AccountingServiceConfiguration {
	@Bean
	public AggregateRepository<Account, AccountCommand> accountRepositorySync(EventuateAggregateStore aggregateStore) {
		return new AggregateRepository<>(Account.class, aggregateStore);
	}

	@Bean
	public AccountingService accountingService() {
		return new AccountingService();
	}
}
