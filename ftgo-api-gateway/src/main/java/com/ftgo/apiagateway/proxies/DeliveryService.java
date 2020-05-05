package com.ftgo.apiagateway.proxies;

import org.springframework.stereotype.Service;

import com.ftgo.apiagateway.model.DeliveryInfo;

import reactor.core.publisher.Mono;

/**
 * The proxy class of the delivery service.
 * 
 * @author  Wuyi Chen
 * @date    05/05/2020
 * @version 1.0
 * @since   1.0
 */
@Service
public class DeliveryService {
	public Mono<DeliveryInfo> findDeliveryByOrderId(String orderId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
