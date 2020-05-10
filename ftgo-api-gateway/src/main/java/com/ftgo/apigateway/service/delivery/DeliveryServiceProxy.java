package com.ftgo.apigateway.service.delivery;

import org.springframework.stereotype.Service;

import com.ftgo.apigateway.model.DeliveryInfo;

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
public class DeliveryServiceProxy {
	public Mono<DeliveryInfo> findDeliveryByOrderId(String orderId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
