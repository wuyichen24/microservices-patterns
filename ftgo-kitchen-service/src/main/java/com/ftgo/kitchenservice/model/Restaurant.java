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
package com.ftgo.kitchenservice.model;

import io.eventuate.tram.events.common.DomainEvent;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.restaurantservice.api.model.MenuItem;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import java.util.List;

/**
 * The entity class for restaurants.
 * 
 * @author  Wuyi Chen
 * @date    04/15/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "kitchen_service_restaurants")
@Access(AccessType.FIELD)
public class Restaurant {
	@Id
	private Long id;

	@Embedded
	@ElementCollection
	@CollectionTable(name = "kitchen_service_restaurant_menu_items")
	private List<MenuItem> menuItems;
	
	public Restaurant () {}     // Keep default constructor for Hibernate

	public Restaurant(long id, List<MenuItem> menuItems) {
		this.id = id;
		this.menuItems = menuItems;
	}

	public List<DomainEvent> reviseMenu(RestaurantMenu revisedMenu) {
		throw new UnsupportedOperationException();
	}

	public void verifyRestaurantDetails(TicketDetails ticketDetails) {
		// TODO - implement me
	}

	public Long getId() {
		return id;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
}
