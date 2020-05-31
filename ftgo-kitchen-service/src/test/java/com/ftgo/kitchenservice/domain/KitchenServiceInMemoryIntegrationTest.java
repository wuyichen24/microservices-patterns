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
package com.ftgo.kitchenservice.domain;

import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;
import io.eventuate.tram.sagas.common.SagaCommandHeaders;
import io.eventuate.tram.testutil.TestMessageConsumer;
import io.eventuate.tram.testutil.TestMessageConsumerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.configuration.KitchenServiceEventConfiguration;
import com.ftgo.kitchenservice.configuration.KitchenServiceWebConfiguration;
import com.ftgo.kitchenservice.model.Restaurant;
import com.ftgo.kitchenservice.repository.RestaurantRepository;

import javax.sql.DataSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The in-memory integration test for the kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KitchenServiceInMemoryIntegrationTest.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KitchenServiceInMemoryIntegrationTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${local.server.port}")
	private int port;

	@Configuration
	@EnableAutoConfiguration
	@Import({ KitchenServiceWebConfiguration.class, KitchenServiceEventConfiguration.class, TramCommandProducerConfiguration.class, TramInMemoryConfiguration.class })
	public static class TestConfiguration {
		@Bean
		public ChannelMapping channelMapping() {
			return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
		}

		@Bean
		public TestMessageConsumerFactory testMessageConsumerFactory() {
			return new TestMessageConsumerFactory();
		}

		@Bean
		public DataSource dataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.H2).addScript("eventuate-tram-embedded-schema.sql")
					.addScript("eventuate-tram-sagas-embedded.sql").build();
		}

	}

	private String baseUrl(String path) {
		return "http://localhost:" + port + path;
	}

	@Autowired
	private CommandProducer commandProducer;

	@Autowired
	private TestMessageConsumerFactory testMessageConsumerFactory;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Test
	public void shouldCreateTicket() {
		long restaurantId = System.currentTimeMillis();
		Restaurant restaurant = new Restaurant(restaurantId, Collections.emptyList());

		restaurantRepository.save(restaurant);

		TestMessageConsumer testMessageConsumer = testMessageConsumerFactory.make();

		long orderId = 999;

		TicketDetails orderDetails = new TicketDetails();
		String messageId = commandProducer.send("kitchenService", null,
				new CreateTicketCommand(restaurantId, orderId, orderDetails), testMessageConsumer.getReplyChannel(),
				withSagaCommandHeaders());

		testMessageConsumer.assertHasReplyTo(messageId);
	}

	private Map<String, String> withSagaCommandHeaders() {
		Map<String, String> result = new HashMap<>();
		result.put(SagaCommandHeaders.SAGA_TYPE, "MySagaType");
		result.put(SagaCommandHeaders.SAGA_ID, "MySagaId");
		return result;
	}
}
