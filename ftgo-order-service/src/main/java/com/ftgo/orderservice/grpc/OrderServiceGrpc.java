package com.ftgo.orderservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import net.chrisrichardson.ftgo.orderservice.api.web.CancelOrderRequest;
import net.chrisrichardson.ftgo.orderservice.api.web.CancelOrderResponse;
import net.chrisrichardson.ftgo.orderservice.api.web.CreateOrderRequest;
import net.chrisrichardson.ftgo.orderservice.api.web.CreateOrderResponse;
import net.chrisrichardson.ftgo.orderservice.api.web.ReviseOrderRequest;
import net.chrisrichardson.ftgo.orderservice.api.web.ReviseOrderResponse;

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
