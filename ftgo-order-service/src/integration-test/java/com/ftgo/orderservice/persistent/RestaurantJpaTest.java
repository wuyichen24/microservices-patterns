package com.ftgo.orderservice.persistent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.ftgo.orderservice.model.Restaurant;
import com.ftgo.orderservice.repository.RestaurantRepository;

import static com.ftgo.orderservice.RestaurantMother.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT_MENU_ITEMS;
import static com.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT_NAME;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderJpaTestConfiguration.class)
public class RestaurantJpaTest {
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Test
	public void shouldSaveRestaurant() {
		transactionTemplate.execute((ts) -> {
			Restaurant restaurant = new Restaurant(AJANTA_ID, AJANTA_RESTAURANT_NAME, AJANTA_RESTAURANT_MENU_ITEMS);
			restaurantRepository.save(restaurant);
			return null;
		});
		transactionTemplate.execute((ts) -> {
			Restaurant restaurant = new Restaurant(AJANTA_ID, AJANTA_RESTAURANT_NAME, AJANTA_RESTAURANT_MENU_ITEMS);
			restaurantRepository.save(restaurant);
			return null;
		});
	}
}
