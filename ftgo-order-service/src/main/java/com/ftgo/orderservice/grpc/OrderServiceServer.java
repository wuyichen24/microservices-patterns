package com.ftgo.orderservice.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftgo.common.model.Address;
import com.ftgo.orderservice.api.controller.model.CancelOrderRequest;
import com.ftgo.orderservice.api.controller.model.CancelOrderResponse;
import com.ftgo.orderservice.api.controller.model.CreateOrderRequest;
import com.ftgo.orderservice.api.controller.model.CreateOrderRequest.LineItem;
import com.ftgo.orderservice.api.controller.model.CreateOrderResponse;
import com.ftgo.orderservice.api.controller.model.ReviseOrderRequest;
import com.ftgo.orderservice.api.controller.model.ReviseOrderResponse;
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

public class OrderServiceServer {
	private static final Logger logger = LoggerFactory
			.getLogger(OrderServiceServer.class);

	private int port = 50051;
	private Server server;
	private OrderService orderService;

	public OrderServiceServer(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostConstruct
	public void start() throws IOException {
		server = ServerBuilder.forPort(port).addService((BindableService) new OrderServiceImpl())
				.build().start();
		logger.info("Server started, listening on " + port);
	}

	@PreDestroy
	public void stop() {
		if (server != null) {
			logger.info("*** shutting down gRPC server since JVM is shutting down");
			server.shutdown();
			logger.info("*** server shut down");
		}
	}

	private class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

		@Override
		public void createOrder(CreateOrderRequest req, StreamObserver<CreateOrderResponse> responseObserver) {
			List<LineItem> lineItemsList = req.getLineItems();
			Order order = orderService.createOrder(
					req.getConsumerId(),
					req.getRestaurantId(),
					new DeliveryInformation(
							req.getDeliveryTime(),
							makeAddress(req.getDeliveryAddress())),
					lineItemsList
							.stream()
							.map(x -> new MenuItemIdAndQuantity(x
									.getMenuItemId(), x.getQuantity()))
							.collect(toList()));
			CreateOrderResponse reply = CreateOrderResponse.newBuilder().setOrderId(order.getId()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		private Address makeAddress(Address address) {
			return new Address(address.getStreet1(),
					nullIfBlank(address.getStreet2()), address.getCity(),
					address.getState(), address.getZip());
		}

		@Override
		public void cancelOrder(CancelOrderRequest req, StreamObserver<CancelOrderResponse> responseObserver) {
			CancelOrderResponse reply = CancelOrderResponse.newBuilder().setMessage("Hello " + req.getName()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		@Override
		public void reviseOrder(ReviseOrderRequest req, StreamObserver<ReviseOrderResponse> responseObserver) {
			ReviseOrderResponse reply = ReviseOrderResponse.newBuilder().setMessage("Hello " + req.getName()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}

	private String nullIfBlank(String s) {
		return StringUtils.isBlank(s) ? null : s;
	}
}