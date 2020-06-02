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

import io.grpc.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.ftgo.common.model.Address;
import com.ftgo.orderservice.controller.model.MenuItemIdAndQuantity;

/**
 * The client for the order service's gRPC.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderServiceClient {
	private static final Logger logger = Logger.getLogger(OrderServiceClient.class.getName());

	private final ManagedChannel channel;
	private final OrderServiceGrpcX.OrderServiceBlockingStub clientStub;

	public OrderServiceClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
		clientStub = OrderServiceGrpcX.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public long createOrder(long consumerId, long restaurantId, List<MenuItemIdAndQuantity> lineItems, Address deliveryAddress, LocalDateTime deliveryTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		OrderServiceProtoX.CreateOrderRequest.Builder builder = OrderServiceProtoX.CreateOrderRequest.newBuilder().setConsumerId(consumerId)
				.setRestaurantId(restaurantId).setDeliveryAddress(makeAddress(deliveryAddress))
				.setDeliveryTime(deliveryTime.format(formatter));
		lineItems.forEach(li -> builder
				.addLineItems(OrderServiceProtoX.LineItem.newBuilder().setQuantity(li.getQuantity()).setMenuItemId(li.getMenuItemId())));
		OrderServiceProtoX.CreateOrderReply response = clientStub.createOrder(builder.build());
		return response.getOrderId();
	}

	private OrderServiceProtoX.Address makeAddress(Address address) {
		OrderServiceProtoX.Address.Builder builder = OrderServiceProtoX.Address.newBuilder().setStreet1(address.getStreet1());
		if (address.getStreet2() != null)
			builder.setStreet2(address.getStreet2());
		builder.setCity(address.getCity()).setState(address.getState()).setZip(address.getZip());
		return builder.build();
	}
}
