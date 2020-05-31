/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ftgo.apigateway.service.delivery;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration class to instantiate the routing rules for the kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableConfigurationProperties(DeliveryDestinations.class)
public class DeliveryConfiguration {
	@Bean
	public RouteLocator deliveryProxyRouting(RouteLocatorBuilder builder, DeliveryDestinations deliveryDestinations) {
		return builder.routes()
				.route(r -> r.path("/deliveries/**").and().method("GET").uri(deliveryDestinations.getDeliveryServiceUrl()))
				.route(r -> r.path("/couriers/**").and().method("POST").uri(deliveryDestinations.getDeliveryServiceUrl()))
				.build();
	}
}
