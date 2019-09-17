package com.ftgo.accountingservice.command;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.accountingservice.command.model.AuthorizeCommandInternal;
import com.ftgo.accountingservice.command.model.ReverseAuthorizationCommandInternal;
import com.ftgo.accountingservice.command.model.ReviseAuthorizationCommandInternal;
import com.ftgo.accountingservice.domain.*;
import com.ftgo.accountingservice.exception.AccountDisabledException;
import com.ftgo.accountingservice.model.Account;
import com.ftgo.accountservice.api.AccountDisabledReply;
import com.ftgo.accountservice.api.command.AuthorizeCommand;
import com.ftgo.accountservice.api.command.ReverseAuthorizationCommand;
import com.ftgo.accountservice.api.command.ReviseAuthorizationCommand;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.sagas.eventsourcingsupport.UpdatingOptionsBuilder.replyingTo;

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
		AuthorizeCommand command = cm.getCommand();

		accountRepository.update(Long.toString(command.getConsumerId()), makeAuthorizeCommandInternal(command),
				replyingTo(cm).catching(AccountDisabledException.class, () -> withFailure(new AccountDisabledReply()))
						.build());
	}

	public void reverseAuthorization(CommandMessage<ReverseAuthorizationCommand> cm) {
		ReverseAuthorizationCommand command = cm.getCommand();

		accountRepository.update(Long.toString(command.getConsumerId()), makeReverseAuthorizeCommandInternal(command),
				replyingTo(cm).catching(AccountDisabledException.class, () -> withFailure(new AccountDisabledReply()))
						.build());
	}

	public void reviseAuthorization(CommandMessage<ReviseAuthorizationCommand> cm) {
		ReviseAuthorizationCommand command = cm.getCommand();

		accountRepository.update(Long.toString(command.getConsumerId()), makeReviseAuthorizeCommandInternal(command),
				replyingTo(cm).catching(AccountDisabledException.class, () -> withFailure(new AccountDisabledReply()))
						.build());
	}

	private AuthorizeCommandInternal makeAuthorizeCommandInternal(AuthorizeCommand command) {
		return new AuthorizeCommandInternal(Long.toString(command.getConsumerId()), Long.toString(command.getOrderId()),
				command.getOrderTotal());
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
