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
package com.ftgo.deliveryservice.model;

import javax.persistence.*;

import com.ftgo.common.model.Address;

/**
 * The entity class for restaurants.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "delivery_service_restaurants")
@Access(AccessType.FIELD)
public class Restaurant {
	@Id
	private Long id;
	private String restaurantName;
	
	@Embedded
	private Address address;

	private Restaurant() {
	}

	public Restaurant(long restaurantId, String restaurantName, Address address) {
		this.id = restaurantId;
		this.restaurantName = restaurantName;
		this.address = address;
	}

	public static Restaurant create(long restaurantId, String restaurantName, Address address) {
		return new Restaurant(restaurantId, restaurantName, address);
	}

	public Address getAddress() {
		return address;
	}
}
