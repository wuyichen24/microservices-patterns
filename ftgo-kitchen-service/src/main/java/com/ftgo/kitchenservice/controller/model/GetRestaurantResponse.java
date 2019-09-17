package com.ftgo.kitchenservice.controller.model;

public class GetRestaurantResponse {
	private long restaurantId;

	public GetRestaurantResponse() {}

	public GetRestaurantResponse(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public long getRestaurantId()                  { return restaurantId;              }
	public void setRestaurantId(long restaurantId) { this.restaurantId = restaurantId; }
}
