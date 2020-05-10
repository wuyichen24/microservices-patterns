package com.ftgo.orderservice.message;

import org.junit.Before;
import org.junit.Test;

import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.orderservice.RestaurantMother;
import com.ftgo.orderservice.event.OrderServiceEventConsumer;
import com.ftgo.orderservice.service.OrderService;
import com.ftgo.restaurantservice.api.event.RestaurantCreatedEvent;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import static com.ftgo.orderservice.RestaurantMother.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT_NAME;
import static io.eventuate.tram.testing.DomainEventHandlerUnitTestSupport.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit test for the event consumer of the order service.
 * 
 * <p>This unit test uses Eventuate Tram Mock Messaging framework and Mockito mocks.
 *
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
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
				.aggregate("com.ftgo.orderservice.model.Restaurant", AJANTA_ID)
				.publishes(new RestaurantCreatedEvent(AJANTA_RESTAURANT_NAME, RestaurantMother.RESTAURANT_ADDRESS, RestaurantMother.AJANTA_RESTAURANT_MENU))
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