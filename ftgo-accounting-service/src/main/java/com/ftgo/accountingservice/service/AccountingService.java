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
package com.ftgo.accountingservice.service;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.SaveOptions;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.accountingservice.command.model.AccountCommand;
import com.ftgo.accountingservice.command.model.CreateAccountCommand;
import com.ftgo.accountingservice.model.Account;

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
		accountRepository.save(new CreateAccountCommand(), Optional.of(new SaveOptions().withId(aggregateId)));
	}
}
