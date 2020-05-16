package com.ftgo.deliveryservice.api.model;

/**
 * The courier availability.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class CourierAvailability {

	private boolean available;

	public CourierAvailability() {
	}

	public CourierAvailability(boolean available) {
		this.available = available;
	}

	public boolean isAvailable()                   { return available;           }
	public void    setAvailable(boolean available) { this.available = available; }
}
