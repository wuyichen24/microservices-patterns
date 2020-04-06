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

import com.ftgo.accountservice.api.command.AuthorizeCommand;
import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.consumerservice.api.command.ValidateOrderByConsumer;
import com.ftgo.kitchenservice.api.command.CancelCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.kitchenservice.api.controller.model.CreateTicketReply;
import com.ftgo.orderservice.OrderDetailsMother;
import com.ftgo.orderservice.RestaurantMother;
import com.ftgo.orderservice.api.controller.model.CreateOrderRequest;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.repository.RestaurantRepository;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.testutil.FtgoTestUtil;

import java.util.Collections;

import static com.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT_MENU;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static io.eventuate.util.test.async.Eventually.eventually;
import static io.restassured.RestAssured.given;
import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@SpringBootTest(classes = OrderServiceComponentTestStepDefinitions.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class OrderServiceComponentTestStepDefinitions {
	private Response response;
	private long     consumerId;

	static {
		CommonJsonMapperInitializer.registerMoneyModule();
	}

	private int    port = 8082;
	private String host = System.getenv("DOCKER_HOST_IP");
	
	@Autowired
	protected SagaParticipantStubManager sagaParticipantStubManager;

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
			return new SagaParticipantChannels("consumerService",
					"kitchenService", "accountingService", "orderService");
		}

		@Bean
		public MessageTracker messageTracker(MessageConsumer messageConsumer) {
			return new MessageTracker(
					singleton("net.chrisrichardson.ftgo.orderservice.domain.Order"),
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
		sagaParticipantStubManager.forChannel("consumerService")
				.when(ValidateOrderByConsumer.class)
				.replyWith(cm -> withSuccess());
	}

	public enum CreditCardType {
		valid, expired
	}

	@Given("using a(.?) (.*) credit card")
	public void useCreditCard(String ignore, CreditCardType creditCard) {
		switch (creditCard) {
		case valid:
			sagaParticipantStubManager.forChannel("accountingService")
					.when(AuthorizeCommand.class).replyWithSuccess();
			break;
		case expired:
			sagaParticipantStubManager.forChannel("accountingService")
					.when(AuthorizeCommand.class).replyWithFailure();
			break;
		default:
			fail("Don't know what to do with this credit card");
		}
	}

	@Given("the restaurant is accepting orders")
	public void restaurantAcceptsOrder() {
		sagaParticipantStubManager
				.forChannel("kitchenService")
				.when(CreateTicketCommand.class)
				.replyWith(
						cm -> withSuccess(new CreateTicketReply(cm.getCommand()
								.getOrderId())))
				.when(ConfirmCreateTicketCommand.class).replyWithSuccess()
				.when(CancelCreateTicketCommand.class).replyWithSuccess();

		if (!restaurantRepository.findById(RestaurantMother.AJANTA_ID).isPresent()) {
			domainEventPublisher
					.publish("net.chrisrichardson.ftgo.restaurantservice.domain.Restaurant",
							RestaurantMother.AJANTA_ID,
							Collections.singletonList(new RestaurantCreatedEvent(
									RestaurantMother.AJANTA_RESTAURANT_NAME, 
									RestaurantMother.RESTAURANT_ADDRESS,
									AJANTA_RESTAURANT_MENU)));

			eventually(() -> {
				FtgoTestUtil.assertPresent(restaurantRepository
						.findById(RestaurantMother.AJANTA_ID));
			});
		}
	}

	@When("I place an order for Chicken Vindaloo at Ajanta")
	public void placeOrder() {
		response = given()
				.body(new CreateOrderRequest(consumerId,
	                    RestaurantMother.AJANTA_ID, OrderDetailsMother.DELIVERY_ADDRESS, OrderDetailsMother.DELIVERY_TIME, Collections.singletonList(
	                            new CreateOrderRequest.LineItem(RestaurantMother.CHICKEN_VINDALOO_MENU_ITEM_ID,
	                                                            OrderDetailsMother.CHICKEN_VINDALOO_QUANTITY))))
				.contentType("application/json").when()
				.post(baseUrl("/orders"));
	}

	@Then("the order should be (.*)")
	public void theOrderShouldBeInState(String desiredOrderState) {
		Integer orderId = this.response.then().statusCode(200).extract().path("orderId");

		assertNotNull(orderId);

		eventually(() -> {
			String state = given().when().get(baseUrl("/orders/" + orderId))
					.then().statusCode(200).extract().path("state");
			assertEquals(desiredOrderState, state);
		});

		sagaParticipantStubManager.verifyCommandReceived("kitchenService", CreateTicketCommand.class);
	}

	@And("an (.*) event should be published")
	public void verifyEventPublished(String expectedEventClass) {
		messageTracker.assertDomainEventPublished(
				"net.chrisrichardson.ftgo.orderservice.domain.Order",
				"net.chrisrichardson.ftgo.orderservice.domain." + expectedEventClass);
	}
}