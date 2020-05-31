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
package com.ftgo.deliveryservice.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.deliveryservice.model.Courier;

import javax.persistence.EntityManager;

/**
 * The custom repository inplementation for {@code Courier} entity. 
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class CustomCourierRepositoryImpl implements CustomCourierRepository {
	@Autowired
	private EntityManager entityManager;

	@Override
	public Courier findOrCreateCourier(long courierId) {
		Courier courier = entityManager.find(Courier.class, courierId);
		if (courier == null) {
			courier = Courier.create(courierId);
			entityManager.persist(courier);
		}
		return courier;
	}
}
