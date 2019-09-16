package com.ftgo.apiagateway.orders;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ftgo.apiagateway.exception.OrderNotFoundException;
import com.ftgo.apiagateway.model.BillInfo;
import com.ftgo.apiagateway.model.DeliveryInfo;
import com.ftgo.apiagateway.model.TicketInfo;
import com.ftgo.apiagateway.services.*;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class OrderHandlers {
	private OrderService orderService;
	private KitchenService    kitchenService;
	private DeliveryService   deliveryService;
	private AccountingService accountingService;

	public OrderHandlers(OrderService orderService, KitchenService kitchenService, DeliveryService deliveryService, AccountingService accountingService) {
		this.orderService      = orderService;
		this.kitchenService    = kitchenService;
		this.deliveryService   = deliveryService;
		this.accountingService = accountingService;
	}

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
