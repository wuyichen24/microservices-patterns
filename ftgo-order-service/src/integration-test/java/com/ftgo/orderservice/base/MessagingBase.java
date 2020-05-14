package com.ftgo.orderservice.base;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.orderservice.OrderTestData;
import com.ftgo.orderservice.api.event.OrderCreatedEvent;
import com.ftgo.orderservice.event.OrderServiceEventPublisher;

import java.util.Collections;

import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_ORDER;
import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_ORDER_DETAILS;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_RESTAURANT_NAME;

/**
 * The base class for the setup phase of the messaging test.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessagingBase.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class MessagingBase {
	@Configuration
	@EnableAutoConfiguration
	@Import({ EventuateContractVerifierConfiguration.class, TramEventsPublisherConfiguration.class, TramInMemoryConfiguration.class })
	public static class TestConfiguration {
		@Bean
		public ChannelMapping channelMapping() {
			return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
		}

		@Bean
		public OrderServiceEventPublisher orderAggregateEventPublisher(DomainEventPublisher eventPublisher) {
			return new OrderServiceEventPublisher(eventPublisher);
		}
	}

	@Autowired
	private OrderServiceEventPublisher orderAggregateEventPublisher;

	protected void orderCreated() {
	    orderAggregateEventPublisher.publish(CHICKEN_VINDALOO_ORDER, Collections.singletonList(new OrderCreatedEvent(CHICKEN_VINDALOO_ORDER_DETAILS, OrderTestData.DELIVERY_ADDRESS, AJANTA_RESTAURANT_NAME)));
	}
}
