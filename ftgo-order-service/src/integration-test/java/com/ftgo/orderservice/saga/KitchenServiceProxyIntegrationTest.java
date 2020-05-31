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
package com.ftgo.orderservice.saga;

import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;
import io.eventuate.tram.sagas.orchestration.SagaCommandProducer;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;
import io.eventuate.tram.springcloudcontractsupport.EventuateTramRoutesConfigurer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.BatchStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.kitchenservice.api.KitchenServiceChannels;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.kitchenservice.api.controller.model.CreateTicketReply;
import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.api.model.TicketLineItem;
import com.ftgo.orderservice.OrderTestData;
import com.ftgo.orderservice.saga.createorder.CreateOrderSaga;

import javax.sql.DataSource;

import java.util.Collections;

import static com.ftgo.orderservice.OrderTestData.CHICKEN_VINDALOO_QUANTITY;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantTestData.CHICKEN_VINDALOO;
import static com.ftgo.orderservice.RestaurantTestData.CHICKEN_VINDALOO_MENU_ITEM_ID;
import static org.junit.Assert.assertEquals;

/**
 * The integration test for sending commands to the kitchen service.
 *
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KitchenServiceProxyIntegrationTest.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = { "com.ftgo:ftgo-kitchen-service-contracts" })
@DirtiesContext
public class KitchenServiceProxyIntegrationTest {
	@Autowired
	private SagaMessagingTestHelper sagaMessagingTestHelper;

	@Test
	public void shouldSuccessfullyCreateTicket() {
		CreateTicketCommand command = new CreateTicketCommand(AJANTA_ID, OrderTestData.ORDER_ID, 
				new TicketDetails(
						Collections.singletonList(new TicketLineItem(
								CHICKEN_VINDALOO_MENU_ITEM_ID,
								CHICKEN_VINDALOO, CHICKEN_VINDALOO_QUANTITY))));
		CreateTicketReply expectedReply = new CreateTicketReply(OrderTestData.ORDER_ID);
		String sagaType = CreateOrderSaga.class.getName();

		CommandEndpoint<CreateTicketCommand> kitchenServiceCreateTicketCommandEndpoint = CommandEndpointBuilder
				.forCommand(CreateTicketCommand.class)
				.withChannel(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL)
				.withReply(CreateTicketReply.class).build();      
		
		CreateTicketReply reply = sagaMessagingTestHelper.sendAndReceiveCommand(kitchenServiceCreateTicketCommandEndpoint, command, CreateTicketReply.class, sagaType);

		assertEquals(expectedReply, reply);
	}
	
	@Configuration
	@EnableAutoConfiguration
	@Import({ TramCommandProducerConfiguration.class, TramInMemoryConfiguration.class, EventuateContractVerifierConfiguration.class })
	public static class TestConfiguration {
		@Bean
		public ChannelMapping channelMapping() {
			return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
		}

		@Bean
		public DataSource dataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.H2)
					.addScript("eventuate-tram-embedded-schema.sql")
					.addScript("eventuate-tram-sagas-embedded.sql").build();
		}

		@Bean
		public EventuateTramRoutesConfigurer eventuateTramRoutesConfigurer(BatchStubRunner batchStubRunner) {
			return new EventuateTramRoutesConfigurer(batchStubRunner);
		}

		@Bean
		public SagaMessagingTestHelper sagaMessagingTestHelper() {
			return new SagaMessagingTestHelper();
		}

		@Bean
		public SagaCommandProducer sagaCommandProducer() {
			return new SagaCommandProducer();
		}
	}
}