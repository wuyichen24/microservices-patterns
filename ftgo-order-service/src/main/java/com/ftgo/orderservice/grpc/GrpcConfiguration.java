package com.ftgo.orderservice.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ftgo.orderservice.service.OrderService;

@Configuration
public class GrpcConfiguration {
	@Bean
	public OrderServiceServer helloWorldServer(OrderService orderService) {
		return new OrderServiceServer(orderService);
	}
}
