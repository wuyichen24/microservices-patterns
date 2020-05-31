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
package com.ftgo.apigateway.service.order;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ftgo.apigateway.exception.OrderNotFoundException;
import com.ftgo.apigateway.model.BillInfo;
import com.ftgo.apigateway.model.DeliveryInfo;
import com.ftgo.apigateway.model.OrderInfo;
import com.ftgo.apigateway.model.TicketInfo;
import com.ftgo.apigateway.service.accounting.AccountingServiceProxy;
import com.ftgo.apigateway.service.delivery.*;
import com.ftgo.apigateway.service.kitchen.KitchenServiceProxy;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * The order handler class defines the request handler methods that implement custom behavior, including API composition.
 * 
 * @author  Wuyi Chen
 * @date    05/04/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderHandlers {
	private OrderServiceProxy      orderService;
	private KitchenServiceProxy    kitchenService;
	private DeliveryServiceProxy   deliveryService;
	private AccountingServiceProxy accountingService;

	/**
	 * Constructs a {@code OrderHandlers} object.
	 * 
	 * @param  orderService
	 *         The proxy class for connecting the order service.
	 * 
	 * @param  kitchenService
	 *         The proxy class for connecting the kitchen service.
	 * 
	 * @param  deliveryService
	 *         The proxy class for connecting the delivery service.
	 * 
	 * @param  accountingService
	 *         The proxy class for connecting the accounting service.
	 */
	public OrderHandlers(OrderServiceProxy orderService, KitchenServiceProxy kitchenService, DeliveryServiceProxy deliveryService, AccountingServiceProxy accountingService) {
		this.orderService      = orderService;
		this.kitchenService    = kitchenService;
		this.deliveryService   = deliveryService;
		this.accountingService = accountingService;
	}

	/**
	 * Performs API composition to retrieve information about an order.
	 * 
	 * <p>This method invokes the four services in parallel and combines the results to create an OrderDetails object.
	 * 
	 * @param  serverRequest
	 *         The Spring WebFlux representation of an HTTP request.
	 *         
	 * @return  The Spring WebFlux representation of an HTTP response.
	 */
	public Mono<ServerResponse> getOrderDetails(ServerRequest serverRequest) {
		String orderId = serverRequest.pathVariable("orderId");

		Mono<OrderInfo>              orderInfo    = orderService.findOrderById(orderId);
		Mono<Optional<TicketInfo>>   ticketInfo   = kitchenService.findTicketById(orderId).map(Optional::of).onErrorReturn(Optional.empty());
		Mono<Optional<DeliveryInfo>> deliveryInfo = deliveryService.findDeliveryByOrderId(orderId).map(Optional::of).onErrorReturn(Optional.empty());
		Mono<Optional<BillInfo>>     billInfo     = accountingService.findBillByOrderId(orderId).map(Optional::of).onErrorReturn(Optional.empty());
		
		Mono<Tuple4<OrderInfo, Optional<TicketInfo>, Optional<DeliveryInfo>, Optional<BillInfo>>> combined = Mono.zip(orderInfo, ticketInfo, deliveryInfo, billInfo);
		Mono<OrderDetails>           orderDetails = combined.map(OrderDetails::makeOrderDetails);

		return orderDetails
				.flatMap(od -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(od)))
				.onErrorResume(OrderNotFoundException.class, e -> ServerResponse.notFound().build());
	}
}
