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
