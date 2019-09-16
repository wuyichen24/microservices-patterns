package com.ftgo.accountingservice.model;

import io.eventuate.Event;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import io.eventuate.tram.sagas.eventsourcingsupport.SagaReplyRequestedEvent;

import java.util.Collections;
import java.util.List;

import com.ftgo.accountingservice.command.AccountCommand;
import com.ftgo.accountingservice.command.AuthorizeCommandInternal;
import com.ftgo.accountingservice.command.CreateAccountCommand;
import com.ftgo.accountingservice.command.ReverseAuthorizationCommandInternal;
import com.ftgo.accountingservice.command.ReviseAuthorizationCommandInternal;
import com.ftgo.accountingservice.event.model.AccountAuthorizedEvent;
import com.ftgo.accountingservice.event.model.AccountCreatedEvent;

import static io.eventuate.EventUtil.events;

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
