package com.ftgo.apiagateway.services;

import org.springframework.stereotype.Service;

import com.ftgo.apiagateway.model.TicketInfo;

import reactor.core.publisher.Mono;

@Service
public class KitchenService {
	public Mono<TicketInfo> findTicketById(String ticketId) {
		return Mono.error(new UnsupportedOperationException());
	}
}
