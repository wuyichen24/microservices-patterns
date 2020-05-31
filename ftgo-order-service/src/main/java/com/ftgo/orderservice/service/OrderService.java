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
package com.ftgo.orderservice.service;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftgo.common.exception.NotYetImplementedException;
import com.ftgo.orderservice.api.event.OrderDomainEvent;
import com.ftgo.orderservice.api.model.OrderDetails;
import com.ftgo.orderservice.api.model.OrderLineItem;
import com.ftgo.orderservice.controller.model.MenuItemIdAndQuantity;
import com.ftgo.orderservice.controller.model.OrderRevision;
import com.ftgo.orderservice.event.OrderServiceEventPublisher;
import com.ftgo.orderservice.exception.InvalidMenuItemIdException;
import com.ftgo.orderservice.exception.OrderNotFoundException;
import com.ftgo.orderservice.exception.RestaurantNotFoundException;
import com.ftgo.orderservice.model.DeliveryInformation;
import com.ftgo.orderservice.model.LineItemQuantityChange;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.model.Restaurant;
import com.ftgo.orderservice.model.RevisedOrder;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.repository.RestaurantRepository;
import com.ftgo.orderservice.saga.cancelorder.CancelOrderSagaData;
import com.ftgo.orderservice.saga.createorder.CreateOrderSagaData;
import com.ftgo.orderservice.saga.reviseorder.ReviseOrderSagaData;
import com.ftgo.restaurantservice.api.model.MenuItem;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * The order service class for creating and managing orders.
 *
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
@Transactional
public class OrderService {
	private OrderRepository                   orderRepository;
	private RestaurantRepository              restaurantRepository;
	private SagaManager<CreateOrderSagaData> createOrderSagaManager;
	private SagaManager<CancelOrderSagaData>  cancelOrderSagaManager;
	private SagaManager<ReviseOrderSagaData>  reviseOrderSagaManager;
	private OrderServiceEventPublisher        orderAggregateEventPublisher;
	private Optional<MeterRegistry>           meterRegistry;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
     * Construct a {@code OrderService}.
     */
	public OrderService(OrderRepository orderRepository,
			DomainEventPublisher eventPublisher,
			RestaurantRepository restaurantRepository,
			SagaManager<CreateOrderSagaData> createOrderSagaManager,
			SagaManager<CancelOrderSagaData> cancelOrderSagaManager,
			SagaManager<ReviseOrderSagaData> reviseOrderSagaManager,
			OrderServiceEventPublisher orderAggregateEventPublisher,
			Optional<MeterRegistry> meterRegistry) {
		this.orderRepository              = orderRepository;
		this.restaurantRepository         = restaurantRepository;
		this.createOrderSagaManager       = createOrderSagaManager;
		this.cancelOrderSagaManager       = cancelOrderSagaManager;
		this.reviseOrderSagaManager       = reviseOrderSagaManager;
		this.orderAggregateEventPublisher = orderAggregateEventPublisher;
		this.meterRegistry                = meterRegistry;
	}

	/**
	 * Create a new order.
	 * 
	 * @param consumerId
	 * @param restaurantId
	 * @param deliveryInformation
	 * @param lineItems
	 * @return
	 */
	public Order createOrder(long consumerId, long restaurantId, DeliveryInformation deliveryInformation, List<MenuItemIdAndQuantity> lineItems) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

		List<OrderLineItem> orderLineItems = makeOrderLineItems(lineItems, restaurant);

		ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents = Order.createOrder(consumerId, restaurant, deliveryInformation, orderLineItems);  // create order by calling the factory method.

		Order order = orderAndEvents.result;
		orderRepository.save(order);                                                                                                                      // insert the new order into database.

		orderAggregateEventPublisher.publish(order, orderAndEvents.events);                                                                               // publish the domain event
		logger.debug("Send OrderCreatedEvent to Order event channel");
		
		OrderDetails orderDetails = new OrderDetails(consumerId, restaurantId, orderLineItems, order.getOrderTotal());

		CreateOrderSagaData data = new CreateOrderSagaData(order.getId(), orderDetails);
		createOrderSagaManager.create(data, Order.class, order.getId());                                                                                  // instantiates the saga orchestrator,
                                                                                                                                                          // then the saga orchestrator sends a command message to the first saga participant,
		                                                                                                                                                  // inserts the saga orchestrator in the database.
		meterRegistry.ifPresent(mr -> mr.counter("placed_orders").increment());

		return order;
	}

	private List<OrderLineItem> makeOrderLineItems(
			List<MenuItemIdAndQuantity> lineItems, Restaurant restaurant) {
		return lineItems
				.stream()
				.map(li -> {
					MenuItem om = restaurant.findMenuItem(li.getMenuItemId())
							.orElseThrow(
									() -> new InvalidMenuItemIdException(li
											.getMenuItemId()));
					return new OrderLineItem(li.getMenuItemId(), om.getName(),
							om.getPrice(), li.getQuantity());
				}).collect(toList());
	}

	public Optional<Order> confirmChangeLineItemQuantity(Long orderId,
			OrderRevision orderRevision) {
		return orderRepository.findById(orderId).map(
				order -> {
					List<OrderDomainEvent> events = order.confirmRevision(orderRevision);
					orderAggregateEventPublisher.publish(order, events);
					logger.debug("Send OrderRevisedEvent to Order event channel");
					return order;
				});
	}

	public void noteReversingAuthorization(Long orderId) {
		// TODO
		throw new NotYetImplementedException();
	}

	/**
	 * Cancel an order
	 * 
	 * @param  orderId
	 *         The order ID to identify which order needs to be cancelled.
	 *         
	 * @return  The order is being cancelled. 
	 */
	public Order cancel(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		CancelOrderSagaData sagaData = new CancelOrderSagaData(order.getConsumerId(), orderId, order.getOrderTotal());
		cancelOrderSagaManager.create(sagaData);
		return order;
	}

	private Order updateOrder(long orderId,
			Function<Order, List<OrderDomainEvent>> updater) {
		return orderRepository.findById(orderId).map(order -> {
			orderAggregateEventPublisher.publish(order, updater.apply(order));
			logger.debug("Send OrderAuthorizedEvent to Order event channel");
			return order;
		}).orElseThrow(() -> new OrderNotFoundException(orderId));
	}

	public void approveOrder(long orderId) {
		updateOrder(orderId, Order::noteApproved);
		meterRegistry.ifPresent(mr -> mr.counter("approved_orders").increment());
	}

	public void rejectOrder(long orderId) {
		updateOrder(orderId, Order::noteRejected);
		meterRegistry
				.ifPresent(mr -> mr.counter("rejected_orders").increment());
	}

	public void beginCancel(long orderId) {
		updateOrder(orderId, Order::cancel);
	}

	public void undoCancel(long orderId) {
		updateOrder(orderId, Order::undoPendingCancel);
	}

	public void confirmCancelled(long orderId) {
		updateOrder(orderId, Order::noteCancelled);
	}

	/**
	 * Revise an order.
	 * 
	 * @param  orderId
	 *         The order ID to identify which order needs to be revised.
	 *         
	 * @param  orderRevision
	 *         The order revision info.
	 *
	 * @return  The order before being revised.
	 */
	public Order reviseOrder(long orderId, OrderRevision orderRevision) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		ReviseOrderSagaData sagaData = new ReviseOrderSagaData(order.getConsumerId(), orderId, null, orderRevision);
		reviseOrderSagaManager.create(sagaData);
		return order;
	}

	public Optional<RevisedOrder> beginReviseOrder(long orderId,
			OrderRevision revision) {
		return orderRepository
				.findById(orderId)
				.map(order -> {
					ResultWithDomainEvents<LineItemQuantityChange, OrderDomainEvent> result = order.revise(revision);
					orderAggregateEventPublisher.publish(order, result.events);
					logger.debug("Send OrderRevisionProposedEvent to Order event channel");
					return new RevisedOrder(order, result.result);
				});
	}

	public void undoPendingRevision(long orderId) {
		updateOrder(orderId, Order::rejectRevision);
	}

	public void confirmRevision(long orderId, OrderRevision revision) {
		updateOrder(orderId, order -> order.confirmRevision(revision));
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void createMenu(long id, String name, RestaurantMenu menu) {
		Restaurant restaurant = new Restaurant(id, name, menu.getMenuItems());
		restaurantRepository.save(restaurant);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void reviseMenu(long id, RestaurantMenu revisedMenu) {
		// TODO
		restaurantRepository.findById(id).map(restaurant -> {
			List<OrderDomainEvent> events = restaurant.reviseMenu(revisedMenu);
			return restaurant;
		}).orElseThrow(RuntimeException::new);
	}

}
