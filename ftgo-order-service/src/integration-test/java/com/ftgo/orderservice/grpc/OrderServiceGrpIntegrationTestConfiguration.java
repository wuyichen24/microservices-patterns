package com.ftgo.orderservice.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.orderservice.configuration.OrderServiceGrpcConfiguration;
import com.ftgo.orderservice.service.OrderService;

import static org.mockito.Mockito.mock;

/**
 * The configuration class to instantiate and wire the domain service.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@Import(OrderServiceGrpcConfiguration.class)
public class OrderServiceGrpIntegrationTestConfiguration {
	@Bean
	public OrderService orderService() {
		return mock(OrderService.class);
	}
}
