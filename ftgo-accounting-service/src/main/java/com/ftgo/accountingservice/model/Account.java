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
package com.ftgo.accountingservice.model;

import io.eventuate.Event;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import io.eventuate.tram.sagas.eventsourcingsupport.SagaReplyRequestedEvent;

import java.util.Collections;
import java.util.List;

import com.ftgo.accountingservice.command.model.AccountCommand;
import com.ftgo.accountingservice.command.model.AuthorizeCommandInternal;
import com.ftgo.accountingservice.command.model.CreateAccountCommand;
import com.ftgo.accountingservice.command.model.ReverseAuthorizationCommandInternal;
import com.ftgo.accountingservice.command.model.ReviseAuthorizationCommandInternal;
import com.ftgo.accountingservice.event.model.AccountAuthorizedEvent;
import com.ftgo.accountingservice.event.model.AccountCreatedEvent;

import static io.eventuate.EventUtil.events;

/**
 * The entity class for accounts
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class Account extends ReflectiveMutableCommandProcessingAggregate<Account, AccountCommand> {
	public List<Event> process(CreateAccountCommand command) {
		return events(new AccountCreatedEvent());
	}

	public void apply(AccountCreatedEvent event) {

	}

	public List<Event> process(AuthorizeCommandInternal command) {
		return events(new AccountAuthorizedEvent());
	}

	public List<Event> process(ReverseAuthorizationCommandInternal command) {
		return Collections.emptyList();
	}

	public List<Event> process(ReviseAuthorizationCommandInternal command) {
		return Collections.emptyList();
	}

	public void apply(AccountAuthorizedEvent event) {

	}

	public void apply(SagaReplyRequestedEvent event) {
		// TODO - need a way to not need this method
	}
}
