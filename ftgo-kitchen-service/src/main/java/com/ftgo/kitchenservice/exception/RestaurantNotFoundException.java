package com.ftgo.kitchenservice.exception;

/**
 * The exception to indicate that the restaurant can not be found by ID.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class RestaurantNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(long restaurantId) {
		super("Restaurant not found with id " + restaurantId);
	}
}
