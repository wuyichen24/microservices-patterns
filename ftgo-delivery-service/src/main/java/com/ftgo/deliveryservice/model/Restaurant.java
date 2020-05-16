package com.ftgo.deliveryservice.model;

import javax.persistence.*;

import com.ftgo.common.model.Address;

/**
 * The entity class for restaurants.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "delivery_service_restaurants")
@Access(AccessType.FIELD)
public class Restaurant {
	@Id
	private Long id;
	private String restaurantName;
	
	@Embedded
	private Address address;

	private Restaurant() {
	}

	public Restaurant(long restaurantId, String restaurantName, Address address) {
		this.id = restaurantId;
		this.restaurantName = restaurantName;
		this.address = address;
	}

	public static Restaurant create(long restaurantId, String restaurantName, Address address) {
		return new Restaurant(restaurantId, restaurantName, address);
	}

	public Address getAddress() {
		return address;
	}
}
