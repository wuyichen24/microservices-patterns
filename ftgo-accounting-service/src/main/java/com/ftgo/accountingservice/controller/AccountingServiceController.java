package com.ftgo.accountingservice.controller;

import io.eventuate.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(path = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<GetAccountResponse> getAccount(@PathVariable String accountId) {
		try {
			return new ResponseEntity<>(new GetAccountResponse(accountId), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
