package com.ftgo.deliveryservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.ftgo.deliveryservice.model.Restaurant;
import com.ftgo.deliveryservice.repository.RestaurantRepository;
import com.ftgo.deliveryservice.service.DeliveryServiceTestData;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantJpaTest.Config.class)
public class RestaurantJpaTest {

	@Configuration
	@EnableJpaRepositories
	@EnableAutoConfiguration
	public static class Config {
	}

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Test
	public void shouldSaveAndLoad() {
		long restaurantId = System.currentTimeMillis();
		Restaurant restaurant = Restaurant.create(restaurantId, "Delicious Indian",
				DeliveryServiceTestData.PICKUP_ADDRESS);
		restaurantRepository.save(restaurant);

		transactionTemplate.execute((ts) -> {
			Restaurant loadedCourier = restaurantRepository.findById(restaurantId).get();
			assertEquals(DeliveryServiceTestData.PICKUP_ADDRESS, loadedCourier.getAddress());
			return null;
		});

	}
}
