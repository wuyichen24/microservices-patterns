package com.ftgo.apiagateway.services;

import org.springframework.stereotype.Service;

import com.ftgo.apiagateway.model.DeliveryInfo;

import reactor.core.publisher.Mono;

@Service
public class DeliveryService {
	public Mono<DeliveryInfo> findDeliveryByOrderId(String orderId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
