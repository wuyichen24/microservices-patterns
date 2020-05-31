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
package com.ftgo.accountingservice.controller;

import io.eventuate.EntityNotFoundException;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftgo.accountingservice.controller.model.GetAccountResponse;

/**
 * The controller class for defining the external APIs about accounts.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(path = "/accounts")
public class AccountingServiceController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping(path = "/{accountId}")
	@ApiOperation(value = "Get an account by account ID.", response = GetAccountResponse.class)
	public ResponseEntity<GetAccountResponse> getAccount(@PathVariable String accountId) {
		logger.debug("GET /accounts/{accountId} - Get an account by account ID");
		
		try {
			return new ResponseEntity<>(new GetAccountResponse(accountId), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
