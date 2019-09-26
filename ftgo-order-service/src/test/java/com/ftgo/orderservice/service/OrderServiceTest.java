package com.ftgo.orderservice.service;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaManager;

import org.junit.Before;
import org.junit.Test;

import com.ftgo.orderservice.OrderDetailsMother;
import com.ftgo.orderservice.RestaurantMother;
import com.ftgo.orderservice.api.event.OrderCreatedEvent;
import com.ftgo.orderservice.event.OrderDomainEventPublisher;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.repository.RestaurantRepository;
import com.ftgo.orderservice.saga.cancelorder.CancelOrderSagaData;
import com.ftgo.orderservice.saga.createorder.CreateOrderSagaState;
import com.ftgo.orderservice.saga.reviseorder.ReviseOrderSagaData;
import com.ftgo.orderservice.service.OrderService;

import java.util.Collections;
import java.util.Optional;

import static com.ftgo.orderservice.OrderDetailsMother.CHICKEN_VINDALOO_MENU_ITEMS_AND_QUANTITIES;
import static com.ftgo.orderservice.OrderDetailsMother.CHICKEN_VINDALOO_ORDER_DETAILS;
import static com.ftgo.orderservice.OrderDetailsMother.CONSUMER_ID;
import static com.ftgo.orderservice.OrderDetailsMother.ORDER_ID;
import static com.ftgo.orderservice.RestaurantMother.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
	private OrderService orderService;
	private OrderRepository orderRepository;
	private DomainEventPublisher eventPublisher;
	private RestaurantRepository restaurantRepository;
	private SagaManager<CreateOrderSagaState> createOrderSagaManager;
	private SagaManager<CancelOrderSagaData> cancelOrderSagaManager;
	private SagaManager<ReviseOrderSagaData> reviseOrderSagaManager;
	private OrderDomainEventPublisher orderAggregateEventPublisher;

	@Before
	public void setup() {
		orderRepository        = mock(OrderRepository.class);
		eventPublisher         = mock(DomainEventPublisher.class);
		restaurantRepository   = mock(RestaurantRepository.class);
		createOrderSagaManager = mock(SagaManager.class);
		cancelOrderSagaManager = mock(SagaManager.class);
		reviseOrderSagaManager = mock(SagaManager.class);

		// Mock DomainEventPublisher AND use the real OrderDomainEventPublisher

		orderAggregateEventPublisher = mock(OrderDomainEventPublisher.class);

		orderService = new OrderService(orderRepository, eventPublisher,
				restaurantRepository, createOrderSagaManager,
				cancelOrderSagaManager, reviseOrderSagaManager,
				orderAggregateEventPublisher, Optional.empty());
	}

	@Test
	public void shouldCreateOrder() {
		when(restaurantRepository.findById(AJANTA_ID)).thenReturn(Optional.of(AJANTA_RESTAURANT));
		when(orderRepository.save(any(Order.class))).then(invocation -> {
			Order order = (Order) invocation.getArguments()[0];
			order.setId(ORDER_ID);
			return order;
		});

		Order order = orderService.createOrder(CONSUMER_ID, AJANTA_ID, OrderDetailsMother.DELIVERY_INFORMATION, CHICKEN_VINDALOO_MENU_ITEMS_AND_QUANTITIES);

		verify(orderRepository).save(same(order));

		verify(orderAggregateEventPublisher).publish(order,
	            Collections.singletonList(new OrderCreatedEvent(CHICKEN_VINDALOO_ORDER_DETAILS, OrderDetailsMother.DELIVERY_ADDRESS, RestaurantMother.AJANTA_RESTAURANT_NAME)));

		verify(createOrderSagaManager).create(
				new CreateOrderSagaState(ORDER_ID,
						CHICKEN_VINDALOO_ORDER_DETAILS), Order.class, ORDER_ID);
	}
}