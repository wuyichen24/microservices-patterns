package com.ftgo.apigateway.service.kitchen;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ftgo.apigateway.model.TicketInfo;

import reactor.core.publisher.Mono;

/**
 * The proxy class of the kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    05/05/2020
 * @version 1.0
 * @since   1.0
 */
@Service
public class KitchenServiceProxy {
	private KitchenDestinations kitchenDestinations;
	private WebClient           client;

	/**
	 * Constructs a {@code KitchenServiceProxy} object.
	 * 
	 * @param  kitchenDestinations
	 *         The destination of the kitchen service.
	 *         
	 * @param  client
	 *         The HTTP client for performing HTTP requests 
	 */
	public KitchenServiceProxy(KitchenDestinations kitchenDestinations, WebClient client) {
		this.kitchenDestinations = kitchenDestinations;
		this.client              = client;
	}
	
	public Mono<TicketInfo> findTicketById(String ticketId) {
		// TODO
		return Mono.error(new UnsupportedOperationException());
	}
}
