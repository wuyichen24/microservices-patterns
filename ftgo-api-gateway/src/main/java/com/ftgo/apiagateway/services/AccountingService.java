package com.ftgo.apiagateway.services;

import org.springframework.stereotype.Service;

import com.ftgo.apiagateway.model.BillInfo;

import reactor.core.publisher.Mono;

@Service
public class AccountingService {
	public Mono<BillInfo> findBillByOrderId(String orderId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
