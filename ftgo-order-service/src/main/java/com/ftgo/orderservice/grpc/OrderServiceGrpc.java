package com.ftgo.orderservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import net.chrisrichardson.ftgo.orderservice.api.controller.model.CancelOrderRequest;
import net.chrisrichardson.ftgo.orderservice.api.controller.model.CancelOrderResponse;
import net.chrisrichardson.ftgo.orderservice.api.controller.model.CreateOrderRequest;
import net.chrisrichardson.ftgo.orderservice.api.controller.model.CreateOrderResponse;
import net.chrisrichardson.ftgo.orderservice.api.controller.model.ReviseOrderRequest;
import net.chrisrichardson.ftgo.orderservice.api.controller.model.ReviseOrderResponse;

public class OrderServiceGrpc {
	public class OrderServiceBlockingStub {
		public CreateOrderResponse createOrder(CreateOrderRequest request) {
			return null;
		}
	}

	static abstract class OrderServiceImplBase {
		abstract void createOrder(CreateOrderRequest req, StreamObserver<CreateOrderResponse> responseObserver);
		abstract void cancelOrder(CancelOrderRequest req, StreamObserver<CancelOrderResponse> responseObserver);
		abstract void reviseOrder(ReviseOrderRequest req, StreamObserver<ReviseOrderResponse> responseObserver);
	}
	public static OrderServiceBlockingStub newBlockingStub(ManagedChannel channel) {
		return null;
	}
}
