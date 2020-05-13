package com.ftgo.orderservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ftgo.orderservice.grpc.OrderServiceServer;
import com.ftgo.orderservice.service.OrderService;

/**
 * The configuration class to instantiate and wire the gRPC service.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
public class OrderServiceGrpcConfiguration {
	@Bean
	public OrderServiceServer helloWorldServer(OrderService orderService) {
		return new OrderServiceServer(orderService);
	}
}
