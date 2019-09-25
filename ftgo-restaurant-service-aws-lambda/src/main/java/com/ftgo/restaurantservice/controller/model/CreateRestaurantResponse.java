package com.ftgo.restaurantservice.controller.model;

public class CreateRestaurantResponse {
	private long id;

	public CreateRestaurantResponse() {}
	
	public CreateRestaurantResponse(long id) {
		this.id = id;
	}

	public long getId()        { return id;    }
	public void setId(long id) { this.id = id; }
}
