package com.ftgo.orderhistoryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftgo.orderhistoryservice.controller.model.GetOrderResponse;
import com.ftgo.orderhistoryservice.controller.model.GetOrdersResponse;
import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.domain.OrderHistoryFilter;
import com.ftgo.orderhistoryservice.model.Order;
import com.ftgo.orderhistoryservice.model.OrderHistory;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/orders")
public class OrderHistoryController {
	private OrderHistoryDao orderHistoryDao;

	public OrderHistoryController(OrderHistoryDao orderHistoryDao) {
		this.orderHistoryDao = orderHistoryDao;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<GetOrdersResponse> getOrders(@RequestParam(name = "consumerId") String consumerId) {
		OrderHistory orderHistory = orderHistoryDao.findOrderHistory(consumerId, new OrderHistoryFilter());
		return new ResponseEntity<>(new GetOrdersResponse(
				orderHistory.getOrders().stream().map(this::makeGetOrderResponse).collect(toList()),
				orderHistory.getStartKey().orElse(null)), HttpStatus.OK);
	}

	private GetOrderResponse makeGetOrderResponse(Order order) {
		return new GetOrderResponse(order.getOrderId(), order.getStatus(), order.getRestaurantId(),
				order.getRestaurantName());
	}

	@RequestMapping(path = "/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<GetOrderResponse> getOrder(@PathVariable String orderId) {
		return orderHistoryDao.findOrder(orderId)
				.map(order -> new ResponseEntity<>(makeGetOrderResponse(order), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
