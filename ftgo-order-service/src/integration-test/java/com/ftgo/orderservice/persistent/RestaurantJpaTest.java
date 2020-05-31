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
package com.ftgo.orderservice.persistent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.ftgo.orderservice.model.Restaurant;
import com.ftgo.orderservice.repository.RestaurantRepository;

import static com.ftgo.orderservice.RestaurantTestData.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_RESTAURANT_MENU_ITEMS;
import static com.ftgo.orderservice.RestaurantTestData.AJANTA_RESTAURANT_NAME;

/**
 * The integration test that verifies that an Restaurant can be persisted.
 *
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
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
