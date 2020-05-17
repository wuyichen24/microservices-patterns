package com.ftgo.restaurantservice.controller.model;

/**
 * The response for creating restaurant API.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateRestaurantResponse {
	private long id;

	public CreateRestaurantResponse() {}
	
	public CreateRestaurantResponse(long id) {
		this.id = id;
	}

	public long getId()        { return id;    }
	public void setId(long id) { this.id = id; }
}
