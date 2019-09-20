package com.ftgo.orderservice.message;

import net.chrisrichardson.ftgo.restaurantservice.events.RestaurantCreated;
import net.chrisrichardson.ftgo.restaurantservice.events.RestaurantMenu;

import org.junit.Before;
import org.junit.Test;

import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.orderservice.RestaurantMother;
import com.ftgo.orderservice.event.OrderServiceEventConsumer;
import com.ftgo.orderservice.service.OrderService;

import static com.ftgo.orderservice.RestaurantMother.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT_NAME;
import static io.eventuate.tram.testing.DomainEventHandlerUnitTestSupport.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OrderEventConsumerTest {
	private OrderService       orderService;
	private OrderServiceEventConsumer orderEventConsumer;

	@Before
	public void setUp() throws Exception {
		orderService       = mock(OrderService.class);
		orderEventConsumer = new OrderServiceEventConsumer(orderService);
	}

	@Test
	public void shouldCreateMenu() {
		CommonJsonMapperInitializer.registerMoneyModule();

		given().eventHandlers(orderEventConsumer.domainEventHandlers())
				.when()
				.aggregate("net.chrisrichardson.ftgo.restaurantservice.domain.Restaurant", AJANTA_ID)
				.publishes(new RestaurantCreated(AJANTA_RESTAURANT_NAME, RestaurantMother.AJANTA_RESTAURANT_MENU))
				.then()
				.verify(() -> {
					verify(orderService)
							.createMenu(
									AJANTA_ID,
									AJANTA_RESTAURANT_NAME,
									new RestaurantMenu(RestaurantMother.AJANTA_RESTAURANT_MENU_ITEMS));
				});

	}
}