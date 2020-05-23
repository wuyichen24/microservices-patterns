package com.ftgo.kitchenservice.controller.model;

import java.util.List;

import com.ftgo.restaurantservice.api.model.MenuItem;

/**
 * The response for getting restaurant API.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class GetRestaurantResponse {
	private long           restaurantId;
	private List<MenuItem> menuItems;

	public GetRestaurantResponse() {}

	public GetRestaurantResponse(long restaurantId, List<MenuItem> menuItems) {
		this.restaurantId = restaurantId;
		this.menuItems    = menuItems;
	}
	
	public long           getRestaurantId()                      { return restaurantId;              }
	public void           setRestaurantId(long restaurantId)     { this.restaurantId = restaurantId; }
	public List<MenuItem> getMenuItems()                         { return menuItems;                 }
	public void           setMenuItems(List<MenuItem> menuItems) { this.menuItems = menuItems;       }
	
}
