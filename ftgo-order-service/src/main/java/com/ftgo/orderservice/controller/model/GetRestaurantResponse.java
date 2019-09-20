package com.ftgo.orderservice.controller.model;

public class GetRestaurantResponse {
	private long restaurantId;

	public GetRestaurantResponse(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public long getRestaurantId()                  { return restaurantId;              }
	public void setRestaurantId(long restaurantId) { this.restaurantId = restaurantId; }
}
