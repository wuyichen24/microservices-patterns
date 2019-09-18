package com.ftgo.orderhistoryservice.event;

import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.dao.dynamodb.SourceEvent;
import com.ftgo.orderhistoryservice.message.OrderHistoryServiceMessageConfiguration;
import com.ftgo.orderhistoryservice.model.Order;

import java.util.Optional;

import static io.eventuate.util.test.async.Eventually.eventually;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderHistoryEventHandlersTest.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = { "net.chrisrichardson.ftgo:ftgo-order-service-contracts" })
@DirtiesContext
public class OrderHistoryEventHandlersTest {
	@Configuration
	@EnableAutoConfiguration
	@Import({ OrderHistoryServiceMessageConfiguration.class, TramCommandProducerConfiguration.class,
			TramInMemoryConfiguration.class, EventuateContractVerifierConfiguration.class })
	public static class TestConfiguration {
		@Bean
		public ChannelMapping channelMapping() {
			return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
		}

		@Bean
		public OrderHistoryDao orderHistoryDao() {
			return mock(OrderHistoryDao.class);
		}
	}

	@Autowired
	private StubFinder stubFinder;

	@Autowired
	private OrderHistoryDao orderHistoryDao;

	@Test
	public void shouldHandleOrderCreatedEvent() throws InterruptedException {
		when(orderHistoryDao.addOrder(any(Order.class), any(Optional.class))).thenReturn(false);

		stubFinder.trigger("orderCreatedEvent");

		eventually(() -> {
			ArgumentCaptor<Order> orderArg = ArgumentCaptor.forClass(Order.class);
			ArgumentCaptor<Optional<SourceEvent>> sourceEventArg = ArgumentCaptor.forClass(Optional.class);
			verify(orderHistoryDao).addOrder(orderArg.capture(), sourceEventArg.capture());

			Order order = orderArg.getValue();
			Optional<SourceEvent> sourceEvent = sourceEventArg.getValue();

			assertEquals("Ajanta", order.getRestaurantName());
		});
	}
}
