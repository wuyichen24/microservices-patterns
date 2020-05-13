package com.ftgo.orderservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ftgo.orderservice.grpc.OrderServiceServer;
import com.ftgo.orderservice.service.OrderService;

@Configuration
public class OrderServiceGrpcConfiguration {
	@Bean
	public OrderServiceServer helloWorldServer(OrderService orderService) {
		return new OrderServiceServer(orderService);
	}
}
