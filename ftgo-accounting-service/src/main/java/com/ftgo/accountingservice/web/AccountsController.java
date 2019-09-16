package com.ftgo.accountingservice.web;

import io.eventuate.EntityNotFoundException;
import io.eventuate.sync.AggregateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftgo.accountingservice.command.AccountCommand;
import com.ftgo.accountingservice.event.Account;

@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {
	@Autowired
	private AggregateRepository<Account, AccountCommand> accountRepository;

	@RequestMapping(path = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<GetAccountResponse> getAccount(@PathVariable String accountId) {
		try {
			return new ResponseEntity<>(new GetAccountResponse(accountId), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
