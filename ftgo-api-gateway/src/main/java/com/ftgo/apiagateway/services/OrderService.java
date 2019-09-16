package com.ftgo.apiagateway.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.ftgo.apiagateway.exception.OrderNotFoundException;
import com.ftgo.apiagateway.orders.OrderDestinations;
import com.ftgo.apiagateway.orders.OrderInfo;

import reactor.core.publisher.Mono;

@Service
public class OrderService {
	private OrderDestinations orderDestinations;
	private WebClient         client;

	public OrderService(OrderDestinations orderDestinations, WebClient client) {
		this.orderDestinations = orderDestinations;
		this.client = client;
	}

	public Mono<OrderInfo> findOrderById(String orderId) {
		Mono<ClientResponse> response = client.get()
				.uri(orderDestinations.getOrderServiceUrl() + "/orders/{orderId}", orderId).exchange();
		return response.flatMap(resp -> {
			switch (resp.statusCode()) {
			case OK:
				return resp.bodyToMono(OrderInfo.class);
			case NOT_FOUND:
				return Mono.error(new OrderNotFoundException());
			default:
				return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
			}
		});
	}
}
