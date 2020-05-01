package com.ftgo.orderhistoryservice.message;

import io.eventuate.tram.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.domain.CommonConfiguration;
import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.event.OrderHistoryEventHandlers;

@Configuration
@Import({ CommonConfiguration.class, TramNoopDuplicateMessageDetectorConfiguration.class, DomainEventDispatcherFactory.class })
public class OrderHistoryServiceMessageConfiguration {
	@Bean
	public OrderHistoryEventHandlers orderHistoryEventHandlers(OrderHistoryDao orderHistoryDao) {
		return new OrderHistoryEventHandlers(orderHistoryDao);
	}

	@Bean
	public DomainEventDispatcher orderHistoryDomainEventDispatcher(OrderHistoryEventHandlers orderHistoryEventHandlers, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("orderHistoryDomainEventDispatcher", orderHistoryEventHandlers.domainEventHandlers());
	}
}
