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
package com.ftgo.orderservice.configuration;

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

import com.ftgo.common.configuration.CommonConfiguration;
import com.ftgo.orderservice.event.OrderServiceEventPublisher;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.repository.RestaurantRepository;
import com.ftgo.orderservice.saga.cancelorder.CancelOrderSaga;
import com.ftgo.orderservice.saga.cancelorder.CancelOrderSagaData;
import com.ftgo.orderservice.saga.createorder.CreateOrderSaga;
import com.ftgo.orderservice.saga.createorder.CreateOrderSagaData;
import com.ftgo.orderservice.saga.reviseorder.ReviseOrderSaga;
import com.ftgo.orderservice.saga.reviseorder.ReviseOrderSagaData;
import com.ftgo.orderservice.service.OrderService;

import java.util.Optional;

/**
 * The configuration class to instantiate and wire the domain service class.
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@Import({ TramEventsPublisherConfiguration.class, SagaOrchestratorConfiguration.class, CommonConfiguration.class })
public class OrderServiceConfiguration {
	@Bean
	public SagaCommandProducer sagaCommandProducer() {
		return new SagaCommandProducer();
	}

	@Bean
	public OrderService orderService(RestaurantRepository restaurantRepository,
			OrderRepository orderRepository,
			DomainEventPublisher eventPublisher,
			SagaManager<CreateOrderSagaData> createOrderSagaManager,
			SagaManager<CancelOrderSagaData> cancelOrderSagaManager,
			SagaManager<ReviseOrderSagaData> reviseOrderSagaManager,
			OrderServiceEventPublisher orderAggregateEventPublisher,
			Optional<MeterRegistry> meterRegistry) {
		return new OrderService(orderRepository, eventPublisher,
				restaurantRepository, createOrderSagaManager,
				cancelOrderSagaManager, reviseOrderSagaManager,
				orderAggregateEventPublisher, meterRegistry);
	}

	@Bean
	public SagaManager<CreateOrderSagaData> createOrderSagaManager(CreateOrderSaga saga) {
		return new SagaManagerImpl<>(saga);
	}

	@Bean
	public CreateOrderSaga createOrderSaga() {
		return new CreateOrderSaga();
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
	public MeterRegistryCustomizer<?> meterRegistryCustomizer(@Value("${spring.application.name}") String serviceName) {
		return registry -> registry.config().commonTags("service", serviceName);
	}
}
