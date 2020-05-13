package com.ftgo.orderservice.controller.model;

/**
 * The response for getting restaurant API.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class GetRestaurantResponse {
	private long restaurantId;

	public GetRestaurantResponse(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public long getRestaurantId()                  { return restaurantId;              }
	public void setRestaurantId(long restaurantId) { this.restaurantId = restaurantId; }
}
