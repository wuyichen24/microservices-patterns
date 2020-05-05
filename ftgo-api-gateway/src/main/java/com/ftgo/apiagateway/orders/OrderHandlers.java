package com.ftgo.apiagateway.orders;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ftgo.apiagateway.exception.OrderNotFoundException;
import com.ftgo.apiagateway.model.BillInfo;
import com.ftgo.apiagateway.model.DeliveryInfo;
import com.ftgo.apiagateway.model.TicketInfo;
import com.ftgo.apiagateway.proxies.*;

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
	private OrderService      orderService;
	private KitchenService    kitchenService;
	private DeliveryService   deliveryService;
	private AccountingService accountingService;

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
	public OrderHandlers(OrderService orderService, KitchenService kitchenService, DeliveryService deliveryService, AccountingService accountingService) {
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
