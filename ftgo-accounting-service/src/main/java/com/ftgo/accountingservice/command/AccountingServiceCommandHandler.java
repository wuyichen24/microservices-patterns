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
package com.ftgo.accountingservice.command;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.accountingservice.command.model.AccountCommand;
import com.ftgo.accountingservice.command.model.AuthorizeCommandInternal;
import com.ftgo.accountingservice.command.model.ReverseAuthorizationCommandInternal;
import com.ftgo.accountingservice.command.model.ReviseAuthorizationCommandInternal;
import com.ftgo.accountingservice.exception.AccountDisabledException;
import com.ftgo.accountingservice.model.Account;
import com.ftgo.accountservice.api.AccountDisabledReply;
import com.ftgo.accountservice.api.command.AuthorizeCommand;
import com.ftgo.accountservice.api.command.ReverseAuthorizationCommand;
import com.ftgo.accountservice.api.command.ReviseAuthorizationCommand;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.sagas.eventsourcingsupport.UpdatingOptionsBuilder.replyingTo;

/**
 * The handler class for handling the command messages from other services to the accounting service.
 *
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class AccountingServiceCommandHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AggregateRepository<Account, AccountCommand> accountRepository;

	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel("accountingService")
				.onMessage(AuthorizeCommand.class, this::authorize)
				.onMessage(ReverseAuthorizationCommand.class, this::reverseAuthorization)
				.onMessage(ReviseAuthorizationCommand.class, this::reviseAuthorization).build();
	}

	public void authorize(CommandMessage<AuthorizeCommand> cm) {
		logger.debug("Receive AuthorizeCommand");
		AuthorizeCommand command = cm.getCommand();

		accountRepository.update(Long.toString(command.getConsumerId()),                                                         // the id of the aggregate to update
				makeAuthorizeCommandInternal(command),                                                                           // the command to process
				replyingTo(cm).catching(AccountDisabledException.class, () -> withFailure(new AccountDisabledReply())).build()); // options for updating:
	}                                                                                                                            //    1. Use the message id as an idempotency key to ensure that the message is processed exactly once.
	                                                                                                                             //    2. Add a SagaReplyRequestedEvent pseudo event to the list of events saved in the event store. (for sending a reply to the CreateOrderSaga’s reply channel.)
	                                                                                                                             //    3. Send an AccountDisabledReply instead of the default error reply when the aggregate throws an AccountDisabledException.

	public void reverseAuthorization(CommandMessage<ReverseAuthorizationCommand> cm) {
		logger.debug("Receive ReverseAuthorizationCommand");
		ReverseAuthorizationCommand command = cm.getCommand();

		accountRepository.update(Long.toString(command.getConsumerId()), makeReverseAuthorizeCommandInternal(command),
				replyingTo(cm).catching(AccountDisabledException.class, () -> withFailure(new AccountDisabledReply()))
						.build());
	}

	public void reviseAuthorization(CommandMessage<ReviseAuthorizationCommand> cm) {
		logger.debug("Receive ReviseAuthorizationCommand");
		ReviseAuthorizationCommand command = cm.getCommand();

		accountRepository.update(Long.toString(command.getConsumerId()), makeReviseAuthorizeCommandInternal(command),
				replyingTo(cm).catching(AccountDisabledException.class, () -> withFailure(new AccountDisabledReply()))
						.build());
	}

	private AuthorizeCommandInternal makeAuthorizeCommandInternal(AuthorizeCommand command) {
		return new AuthorizeCommandInternal(Long.toString(command.getConsumerId()), Long.toString(command.getOrderId()),command.getOrderTotal());
	}

	private ReverseAuthorizationCommandInternal makeReverseAuthorizeCommandInternal(
			ReverseAuthorizationCommand command) {
		return new ReverseAuthorizationCommandInternal(Long.toString(command.getConsumerId()),
				Long.toString(command.getOrderId()), command.getOrderTotal());
	}

	private ReviseAuthorizationCommandInternal makeReviseAuthorizeCommandInternal(ReviseAuthorizationCommand command) {
		return new ReviseAuthorizationCommandInternal(Long.toString(command.getConsumerId()),
				Long.toString(command.getOrderId()), command.getOrderTotal());
	}
}
