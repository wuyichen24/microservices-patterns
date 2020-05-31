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

/**
 * The test class for the restaurant repository (Spring JPA).
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
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
		Restaurant restaurant = Restaurant.create(restaurantId, "Delicious Indian", DeliveryServiceTestData.PICKUP_ADDRESS);
		restaurantRepository.save(restaurant);

		transactionTemplate.execute((ts) -> {
			Restaurant loadedCourier = restaurantRepository.findById(restaurantId).get();
			assertEquals(DeliveryServiceTestData.PICKUP_ADDRESS, loadedCourier.getAddress());
			return null;
		});
	}
}
