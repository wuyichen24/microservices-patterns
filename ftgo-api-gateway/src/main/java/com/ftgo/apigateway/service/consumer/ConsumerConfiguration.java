package com.ftgo.apigateway.service.consumer;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration class to instantiate the routing rules for the consumer service.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableConfigurationProperties(ConsumerDestinations.class)
public class ConsumerConfiguration {
	@Bean
	public RouteLocator consumerProxyRouting(RouteLocatorBuilder builder, ConsumerDestinations consumerDestinations) {
		return builder.routes()
				.route(r -> r.path("/consumers").and().method("POST").uri(consumerDestinations.getConsumerServiceUrl()))
				.route(r -> r.path("/consumers/**").and().method("GET").uri(consumerDestinations.getConsumerServiceUrl()))
				.build();
	}
}
