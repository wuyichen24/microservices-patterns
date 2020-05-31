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
package com.ftgo.apigateway.service.accounting;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ftgo.apigateway.model.BillInfo;

import reactor.core.publisher.Mono;

/**
 * The proxy class of the accounting service.
 * 
 * @author  Wuyi Chen
 * @date    05/05/2020
 * @version 1.0
 * @since   1.0
 */
@Service
public class AccountingServiceProxy {
	private AccountingDestinations accountingDestinations;
	private WebClient              client;

	/**
	 * Constructs a {@code AccountingServiceProxy} object.
	 * 
	 * @param  accountingDestinations
	 *         The destination of the accounting service.
	 *         
	 * @param  client
	 *         The HTTP client for performing HTTP requests 
	 */
	public AccountingServiceProxy(AccountingDestinations accountingDestinations, WebClient client) {
		this.accountingDestinations = accountingDestinations;
		this.client                 = client;
	}
	
	public Mono<BillInfo> findBillByOrderId(String orderId) {
		// TODO
		return Mono.error(new UnsupportedOperationException());
	}
}
