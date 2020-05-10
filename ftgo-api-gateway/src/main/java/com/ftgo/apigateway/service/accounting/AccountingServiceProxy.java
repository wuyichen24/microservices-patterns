package com.ftgo.apigateway.service.accounting;

import org.springframework.stereotype.Service;

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
	public Mono<BillInfo> findBillByOrderId(String orderId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
