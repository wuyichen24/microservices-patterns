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

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ftgo.apigateway.model.TicketInfo;

import reactor.core.publisher.Mono;

/**
 * The proxy class of the kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    05/05/2020
 * @version 1.0
 * @since   1.0
 */
@Service
public class KitchenServiceProxy {
	private KitchenDestinations kitchenDestinations;
	private WebClient           client;

	/**
	 * Constructs a {@code KitchenServiceProxy} object.
	 * 
	 * @param  kitchenDestinations
	 *         The destination of the kitchen service.
	 *         
	 * @param  client
	 *         The HTTP client for performing HTTP requests 
	 */
	public KitchenServiceProxy(KitchenDestinations kitchenDestinations, WebClient client) {
		this.kitchenDestinations = kitchenDestinations;
		this.client              = client;
	}
	
	public Mono<TicketInfo> findTicketById(String ticketId) {
		// TODO
		return Mono.error(new UnsupportedOperationException());
	}
}
