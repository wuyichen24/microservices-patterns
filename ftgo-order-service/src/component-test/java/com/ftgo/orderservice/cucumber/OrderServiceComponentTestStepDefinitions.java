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
package com.ftgo.orderservice.cucumber;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.sagas.testing.SagaParticipantStubManagerConfiguration;
import io.eventuate.tram.sagas.testing.SagaParticipantStubManager;
import io.eventuate.tram.testing.MessageTracker;
import io.eventuate.tram.sagas.testing.SagaParticipantChannels;
import io.restassured.response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.ftgo.accountservice.api.AccountingServiceChannels;
import com.ftgo.accountservice.api.command.AuthorizeCommand;
import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.consumerservice.api.ConsumerServiceChannels;
import com.ftgo.consumerservice.api.command.ValidateOrderByConsumerCommand;
import com.ftgo.kitchenservice.api.KitchenServiceChannels;
import com.ftgo.kitchenservice.api.command.CancelCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.kitchenservice.api.controller.model.CreateTicketReply;
import com.ftgo.orderservice.OrderTestData;
import com.ftgo.orderservice.RestaurantTestData;
import com.ftgo.orderservice.api.OrderServiceChannels;
import com.ftgo.orderservice.api.controller.model.CreateOrderRequest;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.repository.RestaurantRepository;
import com.ftgo.restaurantservice.api.RestaurantServiceChannels;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.testutil.FtgoTestUtil;

import java.util.Collections;

import static com.ftgo.orderservice.RestaurantTestData.AJANTA_RESTAURANT_MENU;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static io.eventuate.util.test.async.Eventually.eventually;
import static io.restassured.RestAssured.given;
import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * The step definition class for the component test of the order service.
 * 
 * <p>Cucumber will use this class to match between the executable specifications and the Java testing code.
 * <p>This class defines the meaning of each step in Order Service’s component tests.
 * 
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
@SpringBootTest(classes = OrderServiceComponentTestStepDefinitions.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class OrderServiceComponentTestStepDefinitions {
	private Response response;
	private long     consumerId;

	static {
		CommonJsonMapperInitializer.registerMoneyModule();
	}

	private int    port = 8086;
	private String host = "localhost";
	
	@Autowired
	protected SagaParticipantStubManager sagaParticipantStubManager;     // The helper class that configures stubs for saga participants.

	@Autowired
	protected MessageTracker messageTracker;

	@Autowired
	protected DomainEventPublisher domainEventPublisher;

	@Autowired
	protected RestaurantRepository restaurantRepository;

	protected String baseUrl(String path) {
		return String.format("http://%s:%s%s", host, port, path);
	}

	@Configuration
	@EnableAutoConfiguration
	@Import({ TramJdbcKafkaConfiguration.class, SagaParticipantStubManagerConfiguration.class })
	@EnableJpaRepositories(basePackageClasses = RestaurantRepository.class)
	// Need to verify that the restaurant has been created. Replace with
	// verifyRestaurantCreatedInOrderService
	@EntityScan(basePackageClasses = Order.class)
	public static class TestConfiguration {
		@Bean
		public SagaParticipantChannels sagaParticipantChannels() {
			return new SagaParticipantChannels(ConsumerServiceChannels.CONSUMER_SERVICE_COMMAND_CHANNEL, KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL, 
					AccountingServiceChannels.ACCOUNTING_SERVICE_COMMAND_CHANNEL, OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL);
		}

		@Bean
		public MessageTracker messageTracker(MessageConsumer messageConsumer) {
			return new MessageTracker(
					singleton(OrderServiceChannels.ORDER_EVENT_CHANNEL),
					messageConsumer);
		}

		@Bean
		public ChannelMapping channelMapping() {
			return new DefaultChannelMapping.DefaultChannelMappingBuilder()
					.build();
		}

	}

	@Before
	public void setUp() {
		sagaParticipantStubManager.reset();
	}

	@Given("A valid consumer")
	public void useConsumer() {
		sagaParticipantStubManager.forChannel(ConsumerServiceChannels.CONSUMER_SERVICE_COMMAND_CHANNEL)
				.when(ValidateOrderByConsumerCommand.class)
				.replyWith(cm -> withSuccess());
	}

	public enum CreditCardType {
		valid, expired
	}

	/**
	 * The step of using a credit card.
	 * 
	 * <p>It uses SagaParticipantStubManager to configure the Accounting Service stub 
	 * to reply with either a success or a failure message, depending on the specified credit card.
	 * 
	 * @param ignore
	 * @param creditCard
	 */
	@Given("using a(.?) (.*) credit card")
	public void useCreditCard(String ignore, CreditCardType creditCard) {
		switch (creditCard) {
		case valid:
			sagaParticipantStubManager.forChannel(AccountingServiceChannels.ACCOUNTING_SERVICE_COMMAND_CHANNEL).when(AuthorizeCommand.class).replyWithSuccess();
			break;
		case expired:
			sagaParticipantStubManager.forChannel(AccountingServiceChannels.ACCOUNTING_SERVICE_COMMAND_CHANNEL).when(AuthorizeCommand.class).replyWithFailure();
			break;
		default:
			fail("Don't know what to do with this credit card");
		}
	}

	/**
	 * The step of the restaurant is accepting orders.
	 */
	@Given("the restaurant is accepting orders")
	public void restaurantAcceptsOrder() {
		sagaParticipantStubManager
				.forChannel(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL)
				.when(CreateTicketCommand.class)
				.replyWith(cm -> withSuccess(new CreateTicketReply(cm.getCommand()
								.getOrderId())))
				.when(ConfirmCreateTicketCommand.class).replyWithSuccess()
				.when(CancelCreateTicketCommand.class).replyWithSuccess();

		if (!restaurantRepository.findById(RestaurantTestData.AJANTA_ID).isPresent()) {
			domainEventPublisher
					.publish(RestaurantServiceChannels.RESTAURANT_EVENT_CHANNEL,
							RestaurantTestData.AJANTA_ID,
							Collections.singletonList(new RestaurantCreatedEvent(
									RestaurantTestData.AJANTA_RESTAURANT_NAME, 
									RestaurantTestData.RESTAURANT_ADDRESS,
									AJANTA_RESTAURANT_MENU)));

			eventually(() -> {
				FtgoTestUtil.assertPresent(restaurantRepository
						.findById(RestaurantTestData.AJANTA_ID));
			});
		}
	}

	/**
	 * The step of placing an order.
	 * 
	 * <p>It invokes the Order Service REST API to create Order and saves the response for validation in a later step.
	 */
	@When("I place an order for Chicken Vindaloo at Ajanta")
	public void placeOrder() {
		response = given()
				.body(new CreateOrderRequest(consumerId,
	                    RestaurantTestData.AJANTA_ID, OrderTestData.DELIVERY_ADDRESS, OrderTestData.DELIVERY_TIME, Collections.singletonList(
	                            new CreateOrderRequest.LineItem(RestaurantTestData.CHICKEN_VINDALOO_MENU_ITEM_ID,
	                                                            OrderTestData.CHICKEN_VINDALOO_QUANTITY))))
				.contentType("application/json").when()
				.post(baseUrl("/orders"));
	}

	/**
	 * The step of verifying order state.
	 * 
	 * <p>It verifies that Order was successfully created and that it’s in the expected state.
	 * 
	 * @param desiredOrderState
	 */
	@Then("the order should be (.*)")
	public void theOrderShouldBeInState(String desiredOrderState) {
		Integer orderId = this.response.then().statusCode(200).extract().path("orderId");

		assertNotNull(orderId);

		// verify order state is correct.
		eventually(() -> {
			String state = given().when().get(baseUrl("/orders/" + orderId)).then().statusCode(200).extract().path("state");
			assertEquals(desiredOrderState, state);
		});

		// verify kitchen service has received the command of creating a ticket for the new order.
		sagaParticipantStubManager.verifyCommandReceived(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL, CreateTicketCommand.class);
	}

	
	/**
	 * The step of verifying the order created event has been published.
	 * 
	 * <p>It verifies that the expected domain event was published.
	 * 
	 * @param expectedEventClass
	 */
	@And("an (.*) event should be published")
	public void verifyEventPublished(String expectedEventClass) {
		messageTracker.assertDomainEventPublished(
				OrderServiceChannels.ORDER_EVENT_CHANNEL,
				"com.ftgo.orderservice.event.model" + expectedEventClass);
	}
}
