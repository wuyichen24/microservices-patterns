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
package com.ftgo.restaurantservice.api.controller.model;

import com.ftgo.common.model.Address;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

/**
 * The request for creating restaurant API.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateRestaurantRequest {
	private String         name;
	private Address        address;
	private RestaurantMenu menu;

	public CreateRestaurantRequest() {}
	
	public CreateRestaurantRequest(String name, Address address, RestaurantMenu menu) {
	    this.name    = name;
	    this.address = address;
	    this.menu    = menu;
	}

	public String         getName()                    { return name;      }
	public void           setName(String name)         { this.name = name; }
	public RestaurantMenu getMenu()                    { return menu;      }
	public void           setMenu(RestaurantMenu menu) { this.menu = menu; }
	public Address        getAddress()                 { return address;   }
}
