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
package com.ftgo.endtoendtests;

import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.common.model.Address;
import com.ftgo.common.model.Money;
import com.ftgo.common.model.PersonName;
import com.ftgo.consumerservice.api.controller.model.CreateConsumerRequest;
import com.ftgo.deliveryservice.api.model.CourierAvailability;
import com.ftgo.kitchenservice.api.controller.model.TicketAcceptance;
import com.ftgo.orderservice.api.controller.model.CreateOrderRequest;
import com.ftgo.orderservice.api.controller.model.ReviseOrderRequest;
import com.ftgo.restaurantservice.api.controller.model.CreateRestaurantRequest;
import com.ftgo.restaurantservice.api.model.MenuItem;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;

import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.util.test.async.Eventually;

import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The end-to-end test of the whole FTGO application.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class EndToEndTests {
	public static final String CHICKED_VINDALOO_MENU_ITEM_ID = "1";
	public static final String RESTAURANT_NAME = "My Restaurant";

	private final int revisedQuantityOfChickenVindaloo = 10;
	private String host = System.getenv("DOCKER_HOST_IP");
	private int consumerId;
	private int restaurantId;
	private int orderId;
	private final Money priceOfChickenVindaloo = new Money("12.34");
	private long courierId;

	private String baseUrl(int port, String path, String... pathElements) {
		assertNotNull("host", host);

		StringBuilder sb = new StringBuilder("http://");
		sb.append(host);
		sb.append(":");
		sb.append(port);
		sb.append("/");
		sb.append(path);

		for (String pe : pathElements) {
			sb.append("/");
			sb.append(pe);
		}
		String s = sb.toString();
		System.out.println("url=" + s);
		return s;
	}

	private int apiGatewayPort        = 8080;
	private int accountingServicePort = 8081;
	private int consumerServicePort   = 8082;
	private int deliveryServicePort   = 8083;
	private int kitchenServicePort    = 8084;
	private int orderServicePort      = 8086;
	private int restaurantServicePort = 8087;

	private String consumerBaseUrl(String... pathElements) {
		return baseUrl(consumerServicePort, "consumers", pathElements);
	}

	private String accountingBaseUrl(String... pathElements) {
		return baseUrl(accountingServicePort, "accounts", pathElements);
	}

	private String restaurantBaseUrl(String... pathElements) {
		return baseUrl(restaurantServicePort, "restaurants", pathElements);
	}

	private String kitchenRestaurantBaseUrl(String... pathElements) {
		return kitchenServiceBaseUrl("restaurants", pathElements);
	}

	private String kitchenServiceBaseUrl(String first, String... pathElements) {
		return baseUrl(kitchenServicePort, first, pathElements);
	}

	private String orderBaseUrl(String... pathElements) {
		return baseUrl(apiGatewayPort, "orders", pathElements);
	}

	private String deliveryServiceBaseUrl(String first, String... pathElements) {
		return baseUrl(deliveryServicePort, first, pathElements);
	}

	private String orderRestaurantBaseUrl(String... pathElements) {
		return baseUrl(orderServicePort, "restaurants", pathElements);
	}

	private String orderHistoryBaseUrl(String... pathElements) {
		return baseUrl(apiGatewayPort, "orders", pathElements);
	}

	@BeforeClass
	public static void initialize() {
		CommonJsonMapperInitializer.registerMoneyModule();

		RestAssured.config = RestAssuredConfig.config()
				.objectMapperConfig(
						new ObjectMapperConfig().jackson2ObjectMapperFactory((
								aClass, s) -> JSonMapper.objectMapper));
	}

	@Test
	public void shouldCreateReviseAndCancelOrder() {
		createOrder();
		reviseOrder();
		cancelOrder();
	}

	@Test
	public void shouldDeliverOrder() {
		createOrder();
		noteCourierAvailable();
		acceptTicket();
		assertOrderAssignedToCourier();
	}

	private void reviseOrder() {
		reviseOrder(orderId);
		verifyOrderRevised(orderId);
	}

	private void verifyOrderRevised(int orderId) {
		Eventually.eventually(
				String.format("verifyOrderRevised state %s", orderId),
				() -> {
					String orderTotal = given()
							.when()
							.get(baseUrl(orderServicePort, "orders",
									Integer.toString(orderId))).then()
							.statusCode(200).extract().path("orderTotal");
					assertEquals(
							priceOfChickenVindaloo.multiply(
									revisedQuantityOfChickenVindaloo)
									.asString(), orderTotal);
				});
		Eventually.eventually(
				String.format("verifyOrderRevised state %s", orderId),
				() -> {
					String state = given().when()
							.get(orderBaseUrl(Integer.toString(orderId)))
							.then().statusCode(200).extract()
							.path("orderInfo.state");
					assertEquals("APPROVED", state);
				});
	}

	private void reviseOrder(int orderId) {
		given().body(
				new ReviseOrderRequest(Collections.singletonMap(
						CHICKED_VINDALOO_MENU_ITEM_ID,
						revisedQuantityOfChickenVindaloo)))
				.contentType("application/json").when()
				.post(orderBaseUrl(Integer.toString(orderId), "revise")).then()
				.statusCode(200);
	}

	private void createOrder() {
		consumerId = createConsumer();
		verifyAccountCreatedForConsumer(consumerId);

		restaurantId = createRestaurant();
		verifyRestaurantCreatedInKitchenService(restaurantId);
		verifyRestaurantCreatedInOrderService(restaurantId);

		orderId = createOrder(consumerId, restaurantId);
		verifyOrderAuthorized(orderId);
		verifyOrderHistoryUpdated(orderId, consumerId);
	}

	private void cancelOrder() {
		cancelOrder(orderId);

		verifyOrderCancelled(orderId);
	}

	private void verifyOrderCancelled(int orderId) {
		Eventually.eventually(
				String.format("verifyOrderCancelled %s", orderId),
				() -> {
					String state = given().when()
							.get(orderBaseUrl(Integer.toString(orderId)))
							.then().statusCode(200).extract()
							.path("orderInfo.state");
					assertEquals("CANCELLED", state);
				});
	}

	private void cancelOrder(int orderId) {
		given().body("{}").contentType("application/json").when()
				.post(orderBaseUrl(Integer.toString(orderId), "cancel")).then()
				.statusCode(200);
	}

	private Integer createConsumer() {
		Integer consumerId = given()
				.body(new CreateConsumerRequest(new PersonName("John", "Doe")))
				.contentType("application/json").when().post(consumerBaseUrl())
				.then().statusCode(200).extract().path("consumerId");

		assertNotNull(consumerId);
		return consumerId;
	}

	private void verifyAccountCreatedForConsumer(int consumerId) {
		Eventually.eventually(() -> given().when()
				.get(accountingBaseUrl(Integer.toString(consumerId))).then()
				.statusCode(200));
	}

	private int createRestaurant() {
		Integer restaurantId = given()
				.body(new CreateRestaurantRequest(RESTAURANT_NAME, new Address(
						"1 Main Street", "Unit 99", "Oakland", "CA", "94611"),
						new RestaurantMenu(Collections
								.singletonList(new MenuItem(
										CHICKED_VINDALOO_MENU_ITEM_ID,
										"Chicken Vindaloo",
										priceOfChickenVindaloo)))))
				.contentType("application/json").when()
				.post(restaurantBaseUrl()).then().statusCode(200).extract()
				.path("id");

		assertNotNull(restaurantId);
		return restaurantId;
	}

	private void verifyRestaurantCreatedInKitchenService(int restaurantId) {
		Eventually.eventually(
				String.format("verifyRestaurantCreatedInKitchenService %s",
						restaurantId),
				() -> given()
						.when()
						.get(kitchenRestaurantBaseUrl(Integer
								.toString(restaurantId))).then()
						.statusCode(200));
	}

	private void verifyRestaurantCreatedInOrderService(int restaurantId) {
		Eventually.eventually(
				String.format("verifyRestaurantCreatedInOrderService %s",
						restaurantId),
				() -> given()
						.when()
						.get(orderRestaurantBaseUrl(Integer
								.toString(restaurantId))).then()
						.statusCode(200));
	}

	private int createOrder(int consumerId, int restaurantId) {
		Integer orderId = given()
				.body(new CreateOrderRequest(consumerId, restaurantId,
						new Address("9 Amazing View", null, "Oakland", "CA",
								"94612"), LocalDateTime.now(), Collections
								.singletonList(new CreateOrderRequest.LineItem(
										CHICKED_VINDALOO_MENU_ITEM_ID, 5))))
				.contentType("application/json").when().post(orderBaseUrl())
				.then().statusCode(200).extract().path("orderId");

		assertNotNull(orderId);
		return orderId;
	}

	private void verifyOrderAuthorized(int orderId) {
		Eventually.eventually(
				String.format("verifyOrderApproved %s", orderId),
				() -> {
					String state = given().when()
							.get(orderBaseUrl(Integer.toString(orderId)))
							.then().statusCode(200).extract()
							.path("orderInfo.state");
					assertEquals("APPROVED", state);
				});
	}

	private void verifyOrderHistoryUpdated(int orderId, int consumerId) {
		Eventually.eventually(
				String.format("verifyOrderHistoryUpdated %s", orderId),
				() -> {
					String state = given()
							.when()
							.get(orderHistoryBaseUrl() + "?consumerId=" + consumerId)
							.then()
							.statusCode(200)
							.body("orders[0].restaurantName", equalTo(RESTAURANT_NAME)).extract()
							.path("orders[0].status"); // TODO state?
					assertNotNull(state);
				});
	}

	private void noteCourierAvailable() {
		courierId = System.currentTimeMillis();
		given().body(new CourierAvailability(true))
				.contentType("application/json")
				.when()
				.post(deliveryServiceBaseUrl("couriers", Long.toString(courierId), "availability"))
				.then().statusCode(200);
	}

	private void acceptTicket() {
		courierId = System.currentTimeMillis();
		given().body(new TicketAcceptance(LocalDateTime.now().plusHours(9)))
				.contentType("application/json")
				.when()
				.post(kitchenServiceBaseUrl("tickets", Long.toString(orderId),"accept"))
				.then().statusCode(200);
	}

	private void assertOrderAssignedToCourier() {
		Eventually.eventually(() -> {
			long assignedCourier = given()
					.when()
					.get(deliveryServiceBaseUrl("deliveries",
							Long.toString(orderId))).then().statusCode(200)
					.extract().path("assignedCourier");
			assertThat(assignedCourier).isGreaterThan(0);
		});
	}
}
