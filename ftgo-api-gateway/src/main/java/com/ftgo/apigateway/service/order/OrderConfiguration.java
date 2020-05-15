package com.ftgo.apigateway.service.order;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ftgo.apigateway.service.accounting.AccountingServiceProxy;
import com.ftgo.apigateway.service.delivery.DeliveryServiceProxy;
import com.ftgo.apigateway.service.kitchen.KitchenServiceProxy;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * The configuration class to instantiate the routing rules for the order service.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableConfigurationProperties(OrderDestinations.class)
@Import({ OrderServiceProxy.class, KitchenServiceProxy.class, DeliveryServiceProxy.class, AccountingServiceProxy.class })
public class OrderConfiguration {
	/**
	 * Defines the rules for routing order-related requests.
	 * 
	 * <p>A routing rule can match against some combination of the HTTP method, the headers, and the path.
	 * 
	 * @param builder
	 * @param orderDestinations
	 * @return
	 */
	@Bean
	public RouteLocator orderProxyRouting(RouteLocatorBuilder builder, OrderDestinations orderDestinations) {
		return builder.routes()
				.route(r -> r.path("/orders").and().method("POST").uri(orderDestinations.getOrderServiceUrl()))
				.route(r -> r.path("/orders").and().method("PUT").uri(orderDestinations.getOrderServiceUrl()))
				.route(r -> r.path("/orders/**").and().method("POST").uri(orderDestinations.getOrderServiceUrl()))
				.route(r -> r.path("/orders/**").and().method("PUT").uri(orderDestinations.getOrderServiceUrl()))
				.route(r -> r.path("/orders").and().method("GET").uri(orderDestinations.getOrderHistoryServiceUrl()))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> orderHandlerRouting(OrderHandlers orderHandlers) {
		return RouterFunctions.route(GET("/orders/{orderId}"), orderHandlers::getOrderDetails);
	}

	/**
	 * Inject several proxy classes into the order handler.
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
	 *         
	 * @return  The object of {@code OrderHandler}.
	 */
	@Bean
	public OrderHandlers orderHandlers(OrderServiceProxy orderService, KitchenServiceProxy kitchenService, DeliveryServiceProxy deliveryService, AccountingServiceProxy accountingService) {
		return new OrderHandlers(orderService, kitchenService, deliveryService, accountingService);
	}

	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}
}
