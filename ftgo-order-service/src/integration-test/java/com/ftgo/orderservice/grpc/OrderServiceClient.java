package com.ftgo.orderservice.grpc;

import io.grpc.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.ftgo.common.model.Address;
import com.ftgo.orderservice.api.controller.model.CreateOrderRequest;
import com.ftgo.orderservice.api.controller.model.CreateOrderResponse;
import com.ftgo.orderservice.api.controller.model.CreateOrderRequest.LineItem;
import com.ftgo.orderservice.controller.model.MenuItemIdAndQuantity;
import com.ftgo.orderservice.grpc.OrderServiceGrpc;

public class OrderServiceClient {
	private static final Logger logger = Logger.getLogger(OrderServiceClient.class.getName());

	  private final ManagedChannel channel;
	  private final OrderServiceGrpc.OrderServiceBlockingStub clientStub;

	  public OrderServiceClient(String host, int port) {
	    channel = ManagedChannelBuilder.forAddress(host, port)
	            .usePlaintext()
	            .build();
	    clientStub = OrderServiceGrpc.newBlockingStub(channel);
	  }

	  public void shutdown() throws InterruptedException {
	    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	  }

	  public long createOrder(long consumerId, long restaurantId, List<MenuItemIdAndQuantity> lineItems, Address deliveryAddress, LocalDateTime deliveryTime) {
	    CreateOrderRequest.Builder builder = CreateOrderRequest.newBuilder()
	            .setConsumerId(consumerId)
	            .setRestaurantId(restaurantId)
	            .setDeliveryAddress(makeAddress(deliveryAddress))
	            .setDeliveryTime(deliveryTime);
	    lineItems.forEach(li -> builder.addLineItems(LineItem.newBuilder().setQuantity(li.getQuantity()).setMenuItemId(li.getMenuItemId())));
	    CreateOrderResponse response = clientStub.createOrder(builder.build());
	    return response.getOrderId();
	  }

	  private Address makeAddress(Address address) {
	    Address.Builder builder = Address.newBuilder()
	            .setStreet1(address.getStreet1());
	    if (address.getStreet2() != null)
	      builder.setStreet2(address.getStreet2());
	    builder
	            .setCity(address.getCity())
	            .setState(address.getState())
	            .setZip(address.getZip());
	    return builder.build();
	  }
}
