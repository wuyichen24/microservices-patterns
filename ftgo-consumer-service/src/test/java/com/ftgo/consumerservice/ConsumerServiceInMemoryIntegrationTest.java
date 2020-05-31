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
package com.ftgo.consumerservice;

import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.testutil.TestMessageConsumer;
import io.eventuate.tram.testutil.TestMessageConsumerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.common.model.Money;
import com.ftgo.common.model.PersonName;
import com.ftgo.consumerservice.api.command.ValidateOrderByConsumerCommand;
import com.ftgo.consumerservice.api.controller.model.CreateConsumerRequest;
import com.ftgo.consumerservice.configuration.ConsumerWebConfiguration;

import java.util.Collections;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

/**
 * The in-memory integration test for the consumer service.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerServiceInMemoryIntegrationTest.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConsumerServiceInMemoryIntegrationTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${local.server.port}")
	private int port;

	@Configuration
	@Import({ ConsumerWebConfiguration.class, TramCommandProducerConfiguration.class, TramInMemoryConfiguration.class })
	public static class TestConfiguration {
		@Bean
		public TestMessageConsumerFactory testMessageConsumerFactory() {
			return new TestMessageConsumerFactory();
		}
	}

	private String baseUrl(String path) {
		return "http://localhost:" + port + path;
	}

	@Autowired
	private CommandProducer commandProducer;

	@Autowired
	private TestMessageConsumerFactory testMessageConsumerFactory;

	@Test
	public void shouldCreateConsumer() {
		String postUrl = baseUrl("/consumers");

		Integer consumerId = given().body(new CreateConsumerRequest(new PersonName("John", "Doe")))
				.contentType("application/json").when().post(postUrl).then().statusCode(200).extract()
				.path("consumerId");

		assertNotNull(consumerId);

		TestMessageConsumer testMessageConsumer = testMessageConsumerFactory.make();

		long orderId = 999;
		Money orderTotal = new Money(123);

		String messageId = commandProducer.send("consumerService", null,
				new ValidateOrderByConsumerCommand(consumerId, orderId, orderTotal), testMessageConsumer.getReplyChannel(),
				Collections.emptyMap());

		testMessageConsumer.assertHasReplyTo(messageId);
	}
}
