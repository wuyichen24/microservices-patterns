package com.ftgo.apiagateway.proxies;

import org.springframework.stereotype.Service;

import com.ftgo.apiagateway.model.TicketInfo;

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
public class KitchenService {
	public Mono<TicketInfo> findTicketById(String ticketId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
