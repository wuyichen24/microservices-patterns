package com.ftgo.orderservice.service;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.sagas.orchestration.SagaCommandProducer;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import io.eventuate.tram.sagas.orchestration.SagaManagerImpl;
import io.eventuate.tram.sagas.orchestration.SagaOrchestratorConfiguration;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.domain.CommonConfiguration;
import com.ftgo.orderservice.event.OrderDomainEventPublisher;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.repository.RestaurantRepository;
import com.ftgo.orderservice.saga.cancelorder.CancelOrderSaga;
import com.ftgo.orderservice.saga.cancelorder.CancelOrderSagaData;
import com.ftgo.orderservice.saga.createorder.CreateOrderSaga;
import com.ftgo.orderservice.saga.createorder.CreateOrderSagaState;
import com.ftgo.orderservice.saga.proxy.AccountingServiceProxy;
import com.ftgo.orderservice.saga.proxy.ConsumerServiceProxy;
import com.ftgo.orderservice.saga.proxy.KitchenServiceProxy;
import com.ftgo.orderservice.saga.proxy.OrderServiceProxy;
import com.ftgo.orderservice.saga.reviseorder.ReviseOrderSaga;
import com.ftgo.orderservice.saga.reviseorder.ReviseOrderSagaData;

import java.util.Optional;

@Configuration
@Import({ TramEventsPublisherConfiguration.class,
		SagaOrchestratorConfiguration.class, CommonConfiguration.class })
public class OrderServiceConfiguration {
	@Bean
	public SagaCommandProducer sagaCommandProducer() {
		return new SagaCommandProducer();
	}

	@Bean
	public OrderService orderService(RestaurantRepository restaurantRepository,
			OrderRepository orderRepository,
			DomainEventPublisher eventPublisher,
			SagaManager<CreateOrderSagaState> createOrderSagaManager,
			SagaManager<CancelOrderSagaData> cancelOrderSagaManager,
			SagaManager<ReviseOrderSagaData> reviseOrderSagaManager,
			OrderDomainEventPublisher orderAggregateEventPublisher,
			Optional<MeterRegistry> meterRegistry) {
		return new OrderService(orderRepository, eventPublisher,
				restaurantRepository, createOrderSagaManager,
				cancelOrderSagaManager, reviseOrderSagaManager,
				orderAggregateEventPublisher, meterRegistry);
	}

	@Bean
	public SagaManager<CreateOrderSagaState> createOrderSagaManager(CreateOrderSaga saga) {
		return new SagaManagerImpl<>(saga);
	}

	@Bean
	public CreateOrderSaga createOrderSaga(OrderServiceProxy orderService,
			ConsumerServiceProxy consumerService,
			KitchenServiceProxy kitchenServiceProxy,
			AccountingServiceProxy accountingService) {
		return new CreateOrderSaga(orderService, consumerService, kitchenServiceProxy, accountingService);
	}

	@Bean
	public SagaManager<CancelOrderSagaData> CancelOrderSagaManager(CancelOrderSaga saga) {
		return new SagaManagerImpl<>(saga);
	}

	@Bean
	public CancelOrderSaga cancelOrderSaga() {
		return new CancelOrderSaga();
	}

	@Bean
	public SagaManager<ReviseOrderSagaData> reviseOrderSagaManager(ReviseOrderSaga saga) {
		return new SagaManagerImpl<>(saga);
	}

	@Bean
	public ReviseOrderSaga reviseOrderSaga() {
		return new ReviseOrderSaga();
	}

	@Bean
	public KitchenServiceProxy kitchenServiceProxy() {
		return new KitchenServiceProxy();
	}

	@Bean
	public OrderServiceProxy orderServiceProxy() {
		return new OrderServiceProxy();
	}

	@Bean
	public ConsumerServiceProxy consumerServiceProxy() {
		return new ConsumerServiceProxy();
	}

	@Bean
	public AccountingServiceProxy accountingServiceProxy() {
		return new AccountingServiceProxy();
	}

	@Bean
	public OrderDomainEventPublisher orderAggregateEventPublisher(DomainEventPublisher eventPublisher) {
		return new OrderDomainEventPublisher(eventPublisher);
	}

	@Bean
	public MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}") String serviceName) {
		return registry -> registry.config().commonTags("service", serviceName);
	}
}
