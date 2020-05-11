package com.ftgo.apigateway.service.kitchen;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KitchenDestinations.class)
public class KitchenConfiguration {
	@Bean
	public RouteLocator kitchenProxyRouting(RouteLocatorBuilder builder, KitchenDestinations kitchenDestinations) {
		return builder.routes()
				.route(r -> r.path("/restaurants/**").and().method("GET").uri(kitchenDestinations.getKitchenServiceUrl()))
				.route(r -> r.path("/tickets/**").and().method("POST").uri(kitchenDestinations.getKitchenServiceUrl()))
				.build();
	}
}
