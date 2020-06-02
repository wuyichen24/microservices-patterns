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
package com.ftgo.orderservice.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftgo.common.model.Address;
import com.ftgo.orderservice.controller.model.MenuItemIdAndQuantity;
import com.ftgo.orderservice.model.DeliveryInformation;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.service.OrderService;

import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The gRPC server of the order service.
 * 
 * @author  Wuyi Chen
 * @date    04/24/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderServiceServer {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceServer.class);

	private int port = 50051;
	private Server server;
	private OrderService orderService;

	public OrderServiceServer(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostConstruct
	public void start() throws IOException {
		server = ServerBuilder.forPort(port).addService((BindableService) new OrderServiceImpl()).build().start();
		logger.info("Server started, listening on {}", port);
	}

	@PreDestroy
	public void stop() {
		if (server != null) {
			logger.info("*** shutting down gRPC server since JVM is shutting down");
			server.shutdown();
			logger.info("*** server shut down");
		}
	}

	/**
	 * The implementation of the remote methods.
	 * 
	 * @author  Wuyi Chen
	 * @date    04/24/2020
	 * @version 1.0
	 * @since   1.0
	 */
	public class OrderServiceImpl extends OrderServiceGrpcX.OrderServiceImplBase {
		@Override
		public void createOrder(OrderServiceProtoX.CreateOrderRequest request, StreamObserver<OrderServiceProtoX.CreateOrderReply> responseObserver) {
			List<com.ftgo.orderservice.grpc.OrderServiceProtoX.LineItem> lineItemsList = request.getLineItemsList();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
			LocalDateTime deliveryTime = LocalDateTime.parse(request.getDeliveryTime(), formatter);

			Order order = orderService.createOrder(request.getConsumerId(), request.getRestaurantId(),
					new DeliveryInformation(deliveryTime, convertAddress(request.getDeliveryAddress())),
					lineItemsList.stream().map(x -> new MenuItemIdAndQuantity(x.getMenuItemId(), x.getQuantity())).collect(toList()));
			OrderServiceProtoX.CreateOrderReply reply = OrderServiceProtoX.CreateOrderReply.newBuilder().setOrderId(order.getId()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		@Override
		public void cancelOrder(OrderServiceProtoX.CancelOrderRequest request, StreamObserver<OrderServiceProtoX.CancelOrderReply> responseObserver) {
			OrderServiceProtoX.CancelOrderReply reply = OrderServiceProtoX.CancelOrderReply.newBuilder().setMessage("Hello " + request.getName()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		@Override
		public void reviseOrder(com.ftgo.orderservice.grpc.OrderServiceProtoX.ReviseOrderRequest request, StreamObserver<OrderServiceProtoX.ReviseOrderReply> responseObserver) {
			OrderServiceProtoX.ReviseOrderReply reply = OrderServiceProtoX.ReviseOrderReply.newBuilder().setMessage("Hello " + request.getName()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}

	/**
	 * Convert OrderServiceProto.Address to com.ftgo.common.model.Address.
	 * 
	 * @param  address
	 *         The object of {@code OrderServiceProto.Address}
	 *         
	 * @return  The object of {@code com.ftgo.common.model.Address}
	 */
	private Address convertAddress(OrderServiceProtoX.Address address) {
		return new Address(address.getStreet1(), nullIfBlank(address.getStreet2()), address.getCity(), address.getState(), address.getZip());
	}
	
	private String nullIfBlank(String s) {
		return StringUtils.isBlank(s) ? null : s;
	}
}