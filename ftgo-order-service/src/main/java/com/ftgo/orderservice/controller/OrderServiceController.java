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
package com.ftgo.orderservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftgo.orderservice.api.controller.model.CreateOrderRequest;
import com.ftgo.orderservice.api.controller.model.CreateOrderResponse;
import com.ftgo.orderservice.api.controller.model.ReviseOrderRequest;
import com.ftgo.orderservice.controller.model.GetOrderResponse;
import com.ftgo.orderservice.controller.model.MenuItemIdAndQuantity;
import com.ftgo.orderservice.controller.model.OrderRevision;
import com.ftgo.orderservice.exception.OrderNotFoundException;
import com.ftgo.orderservice.model.*;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.service.OrderService;

import io.swagger.annotations.ApiOperation;

import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * The controller class for defining the external APIs about orders.
 * 
 * @author  Wuyi Chen
 * @date    04/11/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(path = "/orders")
public class OrderServiceController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private OrderService    orderService;
	private OrderRepository orderRepository;

	/**
     * Construct a {@code OrderController}.
     */
	public OrderServiceController(OrderService orderService, OrderRepository orderRepository) {
		this.orderService    = orderService;
		this.orderRepository = orderRepository;
	}

	/**
	 * Create an new order.
	 * 
	 * @param  request
	 *         The {@code CreateOrderRequest} object to capture the information for creating an order.
	 *         
	 * @return  The {@code CreateOrderResponse} object to capture the order ID of the newly created order.
	 */
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Add a new order.", response = CreateOrderResponse.class)
	public CreateOrderResponse create(@RequestBody CreateOrderRequest request) {
		logger.debug("POST /orders - Add a new order");
		
	    Order order = orderService.createOrder(request.getConsumerId(),
	            request.getRestaurantId(),
	            new DeliveryInformation(request.getDeliveryTime(), request.getDeliveryAddress()),
	            request.getLineItems().stream().map(x -> new MenuItemIdAndQuantity(x.getMenuItemId(), x.getQuantity())).collect(toList())
	    );
	    return new CreateOrderResponse(order.getId());
	}

	/**
	 * Get an existing order by order ID.
	 * 
	 * @param  orderId
	 *         The order ID for looking up.
	 * 
	 * @return  The matched order record.
	 */
	
	@RequestMapping(path = "/{orderId}", method = RequestMethod.GET)
	@ApiOperation(value = "Get an order by order ID.", response = GetOrderResponse.class)
	public ResponseEntity<GetOrderResponse> getOrder(@PathVariable long orderId) {
		logger.debug("GET /orders/{orderId} - Get an order by order ID");
		
		Optional<Order> order = orderRepository.findById(orderId);
		return order.map(o -> new ResponseEntity<>(makeGetOrderResponse(o),HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * Cancel an order.
	 * 
	 * @param  orderId
	 *         The order ID to identify which order needs to be cancelled.
	 * 
	 * @return  The cancelled order.
	 */
	@RequestMapping(path = "/{orderId}/cancel", method = RequestMethod.POST)
	@ApiOperation(value = "Cancel an order by order ID.", response = GetOrderResponse.class)
	public ResponseEntity<GetOrderResponse> cancel(@PathVariable long orderId) {
		logger.debug("POST /orders/{orderId}/cancel - Cancel an order by order ID");
		
		try {
			Order order = orderService.cancel(orderId);
			return new ResponseEntity<>(makeGetOrderResponse(order), HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Revise an order.
	 * 
	 * @param  orderId
	 *         The order ID to identify which order needs to be revised.
	 *         
	 * @param  The {@code CreateOrderRequest} object to capture the information for revising the order.
	 * 
	 * @return  The order before being revised.
	 */
	@RequestMapping(path = "/{orderId}/revise", method = RequestMethod.POST)
	@ApiOperation(value = "Revise an order by order ID.", response = GetOrderResponse.class)
	public ResponseEntity<GetOrderResponse> revise(@PathVariable long orderId, @RequestBody ReviseOrderRequest request) {
		logger.debug("POST /orders/{orderId}/revise - Revise an order by order ID");
		
		try {
			Order order = orderService.reviseOrder(orderId, new OrderRevision(Optional.empty(), request.getRevisedLineItemQuantities()));
			return new ResponseEntity<>(makeGetOrderResponse(order), HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Make up a response for an order.
	 * 
	 * @param  order
	 *         The {@code Order} for making up.
	 *         
	 * @return  The {@code GetOrderResponse} object.
	 */
	private GetOrderResponse makeGetOrderResponse(Order order) {
		return new GetOrderResponse(order.getId(), order.getState().name(), order.getOrderTotal());
	}
}

