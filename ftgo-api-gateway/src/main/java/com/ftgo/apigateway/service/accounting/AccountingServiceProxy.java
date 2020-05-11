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
		return Mono.error(new UnsupportedOperationException());
	}
}
