package com.ftgo.orderhistoryservice.message;

import io.eventuate.tram.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.domain.CommonConfiguration;
import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.event.OrderHistoryEventConsumer;

@Configuration
@Import({ CommonConfiguration.class, TramNoopDuplicateMessageDetectorConfiguration.class, DomainEventDispatcherFactory.class })
public class OrderHistoryServiceMessageConfiguration {
	@Bean
	public OrderHistoryEventConsumer orderHistoryEventHandlers(OrderHistoryDao orderHistoryDao) {
		return new OrderHistoryEventConsumer(orderHistoryDao);
	}

	@Bean
	public DomainEventDispatcher orderHistoryDomainEventDispatcher(OrderHistoryEventConsumer orderHistoryEventHandlers, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("orderHistoryDomainEventDispatcher", orderHistoryEventHandlers.domainEventHandlers());
	}
}
