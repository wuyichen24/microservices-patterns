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
