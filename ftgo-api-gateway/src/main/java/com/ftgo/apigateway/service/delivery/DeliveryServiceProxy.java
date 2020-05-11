package com.ftgo.apigateway.service.delivery;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
	private DeliveryDestinations deliveryDestinations;
	private WebClient            client;

	/**
	 * Constructs a {@code DeliveryServiceProxy} object.
	 * 
	 * @param  deliveryDestinations
	 *         The destination of the delivery service.
	 *         
	 * @param  client
	 *         The HTTP client for performing HTTP requests 
	 */
	public DeliveryServiceProxy(DeliveryDestinations deliveryDestinations, WebClient client) {
		this.deliveryDestinations = deliveryDestinations;
		this.client                 = client;
	}
	
	public Mono<DeliveryInfo> findDeliveryByOrderId(String orderId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
