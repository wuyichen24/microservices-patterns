package com.ftgo.accountingservice.services;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.SaveOptions;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.accountingservice.command.AccountCommand;
import com.ftgo.accountingservice.command.CreateAccountCommand;
import com.ftgo.accountingservice.event.Account;

import java.util.Optional;

/**
 * The accounting service for processing operations on accounts.
 *
 * @author  Wuyi Chen
 * @date    09/16/2019
 * @version 1.0
 * @since   1.0
 */
public class AccountingService {
	@Autowired
	private AggregateRepository<Account, AccountCommand> accountRepository;

	public void create(String aggregateId) {
		EntityWithIdAndVersion<Account> account = accountRepository.save(new CreateAccountCommand(),
				Optional.of(new SaveOptions().withId(aggregateId)));
	}
}
