package com.ftgo.accountingservice.configuration;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.command.model.AccountCommand;
import com.ftgo.accountingservice.model.Account;
import com.ftgo.accountingservice.service.AccountingService;
import com.ftgo.common.domain.CommonConfiguration;

/**
 * The configuration class to instantiate and wire the domain service class.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
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
