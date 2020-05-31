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
package com.ftgo.accountingservice.event;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;

import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;

import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.sagas.common.SagaCommandHeaders;
import io.eventuate.tram.testutil.TestMessageConsumer;
import io.eventuate.tram.testutil.TestMessageConsumerFactory;
import io.eventuate.util.test.async.Eventually;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.accountingservice.command.model.AccountCommand;
import com.ftgo.accountingservice.configuration.AccountingServiceEventConfiguration;
import com.ftgo.accountingservice.model.Account;
import com.ftgo.accountservice.api.AccountingServiceChannels;
import com.ftgo.accountservice.api.command.AuthorizeCommand;
import com.ftgo.common.model.Money;
import com.ftgo.consumerservice.api.ConsumerServiceChannels;
import com.ftgo.consumerservice.api.event.model.ConsumerCreatedEvent;

import javax.sql.DataSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Unit test for the event consumer of the accounting service.
 *
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountingServiceCommandHandlerTest.AccountingServiceCommandHandlerTestConfiguration.class)
public class AccountingServiceCommandHandlerTest {
	@Configuration
	@EnableAutoConfiguration
	@Import({ AccountingServiceEventConfiguration.class, TramCommandProducerConfiguration.class,
			EmbeddedTestAggregateStoreConfiguration.class, TramEventsPublisherConfiguration.class, // TODO
			TramInMemoryConfiguration.class })
	static public class AccountingServiceCommandHandlerTestConfiguration {
		@Bean
		public TestMessageConsumerFactory testMessageConsumerFactory() {
			return new TestMessageConsumerFactory();
		}

		@Bean
		public ChannelMapping channelMapping() {
			return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
		}

		@Bean
		public DataSource dataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.H2).addScript("eventuate-tram-embedded-schema.sql")
					.addScript("eventuate-tram-sagas-embedded.sql").addScript("eventuate-embedded-schema.sql").build();
		}
	}

	@Autowired
	private CommandProducer commandProducer;

	@Autowired
	private TestMessageConsumerFactory testMessageConsumerFactory;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	@Autowired
	private AggregateRepository<Account, AccountCommand> accountRepository;

	@Test
	public void shouldReply() throws InterruptedException, ExecutionException, TimeoutException {
		TestMessageConsumer testMessageConsumer = testMessageConsumerFactory.make();

		long consumerId = System.currentTimeMillis();
		long orderId = 102L;

		domainEventPublisher.publish(ConsumerServiceChannels.CONSUMER_EVENT_CHANNEL, consumerId,
				Collections.singletonList(new ConsumerCreatedEvent()));

		Eventually.eventually(() -> {
			accountRepository.find(Long.toString(consumerId));
		});

		Money orderTotal = new Money(123);

		String messageId = commandProducer.send(AccountingServiceChannels.ACCOUNTING_SERVICE_COMMAND_CHANNEL, null,
				new AuthorizeCommand(consumerId, orderId, orderTotal), testMessageConsumer.getReplyChannel(),
				withSagaCommandHeaders());

		testMessageConsumer.assertHasReplyTo(messageId);
	}

	// TODO duplicate
	private Map<String, String> withSagaCommandHeaders() {
		Map<String, String> result = new HashMap<>();
		result.put(SagaCommandHeaders.SAGA_TYPE, "MySagaType");
		result.put(SagaCommandHeaders.SAGA_ID, "MySagaId");
		return result;
	}
}