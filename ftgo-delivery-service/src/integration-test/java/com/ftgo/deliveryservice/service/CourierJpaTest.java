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

import com.ftgo.deliveryservice.model.Action;
import com.ftgo.deliveryservice.model.Courier;
import com.ftgo.deliveryservice.repository.CourierRepository;
import com.ftgo.deliveryservice.service.DeliveryServiceTestData;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The test class for the courier repository (Spring JPA).
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourierJpaTest.Config.class)
public class CourierJpaTest {
	@Configuration
	@EnableJpaRepositories
	@EnableAutoConfiguration
	public static class Config {
	}

	@Autowired
	private CourierRepository courierRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Test
	public void shouldSaveAndLoad() {
		long courierId = System.currentTimeMillis();
		Courier courier = Courier.create(courierId);
		long deliveryId = 103L;
		courier.addAction(Action.makePickup(deliveryId, DeliveryServiceTestData.PICKUP_ADDRESS, LocalDateTime.now()));

		courierRepository.save(courier);

		transactionTemplate.execute((ts) -> {
			Courier loadedCourier = courierRepository.findById(courierId).get();
			assertEquals(1, loadedCourier.getPlan().getActions().size());
			return null;
		});
	}

	@Test
	public void shouldFindAllAvailable() {
		long courierId1 = System.currentTimeMillis() * 10;
		long courierId2 = System.currentTimeMillis() * 10 + 1;
		Courier courier1 = Courier.create(courierId1);
		Courier courier2 = Courier.create(courierId2);

		courier1.noteAvailable();
		courier2.noteUnavailable();

		courierRepository.save(courier1);
		courierRepository.save(courier2);

		List<Courier> availableCouriers = courierRepository.findAllAvailable();

		assertTrue(availableCouriers.stream().anyMatch(c -> c.getId() == courierId1));
		assertFalse(availableCouriers.stream().anyMatch(c -> c.getId() == courierId2));
	}

	@Test
	public void shouldFindOrCreate() {
		long courierId = System.currentTimeMillis();
		transactionTemplate.execute((ts) -> {
			Courier courier = courierRepository.findOrCreateCourier(courierId);
			assertNotNull(courier);
			return null;
		});
		transactionTemplate.execute((ts) -> {
			Courier courier2 = courierRepository.findOrCreateCourier(courierId);
			assertNotNull(courier2);
			return null;
		});
	}
}
