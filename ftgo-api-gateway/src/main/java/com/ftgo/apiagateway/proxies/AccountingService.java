package com.ftgo.apiagateway.proxies;

import org.springframework.stereotype.Service;

import com.ftgo.apiagateway.model.BillInfo;

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
public class AccountingService {
	public Mono<BillInfo> findBillByOrderId(String orderId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
