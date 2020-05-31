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
package com.ftgo.restaurantservice.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ftgo.restaurantservice.api.model.RestaurantMenu;

/**
 * The entity class for restaurants.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "restaurants")
@Access(AccessType.FIELD)
public class Restaurant {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Embedded
	private RestaurantMenu menu;
	
	public Restaurant() {}
	
	public Restaurant(String name, RestaurantMenu menu) {
		this.name = name;
		this.menu = menu;
	}

	public Long   getId()              { return id;        }
	public void   setId(Long id)       { this.id = id;     }
	public String getName()            { return name;      }
	public void   setName(String name) { this.name = name; }

	public RestaurantMenu getMenu() {
		return menu;
	}

	public void setMenu(RestaurantMenu menu) {
		this.menu = menu;
	}
	
}
