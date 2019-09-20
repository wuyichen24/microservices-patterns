package com.ftgo.orderservice.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.orderservice.grpc.GrpcConfiguration;
import com.ftgo.orderservice.service.OrderService;

import static org.mockito.Mockito.mock;

@Configuration
@Import(GrpcConfiguration.class)
public class OrderServiceGrpIntegrationTestConfiguration {
	@Bean
	public OrderService orderService() {
		return mock(OrderService.class);
	}
}
