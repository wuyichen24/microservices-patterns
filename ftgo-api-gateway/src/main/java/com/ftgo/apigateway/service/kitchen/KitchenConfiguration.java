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
