package com.ftgo.orderservice.exception;

public class RestaurantNotFoundException extends RuntimeException {
	public RestaurantNotFoundException(long restaurantId) {
		super("Restaurant not found with id " + restaurantId);
	}
}
