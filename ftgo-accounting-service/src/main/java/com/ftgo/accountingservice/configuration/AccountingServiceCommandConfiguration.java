package com.ftgo.accountingservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.command.AccountingServiceCommandHandler;
import com.ftgo.accountingservice.configuration.model.AccountingServiceChannelConfiguration;

import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.commands.consumer.CommandDispatcherFactory;

/**
 * The configuration class to instantiate and wire the command handler.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableEventHandlers
@Import({CommandDispatcherFactory.class})
public class AccountingServiceCommandConfiguration {
	@Bean
	public AccountingServiceCommandHandler accountCommandHandler() {
		return new AccountingServiceCommandHandler();
	}
	
	@Bean
	public CommandDispatcher commandDispatcher(AccountingServiceCommandHandler target, AccountingServiceChannelConfiguration data, CommandDispatcherFactory commandDispatcherFactory) {
		return commandDispatcherFactory.make(data.getCommandDispatcherId(), target.commandHandlers());
	}
	
	@Bean
	public AccountingServiceChannelConfiguration accountServiceChannelConfiguration() {
		return new AccountingServiceChannelConfiguration("accountCommandDispatcher", "accountCommandChannel");
	}
}
