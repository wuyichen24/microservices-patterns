package com.ftgo.deliveryservice.model;

/**
 * The enum type for the types of delivery actions.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2019
 * @version 1.0
 * @since   1.0
 */
public enum ActionType {
	PICKUP,  // A pickup action tells the Courier to pick up an order from a restaurant at a particular time.
	DROPOFF  // A deliver action tells the Courier to deliver an order to a consumer.
}
