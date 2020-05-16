package com.ftgo.deliveryservice.service;

import com.ftgo.common.model.Address;

/**
 * The static class for hardcoding the test data for testing the delivery service.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class DeliveryServiceTestData {
	static final Address PICKUP_ADDRESS   = new Address("1 Main Street", "Suite 501", "Oakland", "CA", "94612");
	static final Address DELIVERY_ADDRESS = new Address("1 Quiet Street", "Apartment 101", "Oakland", "CA", "94612");
}
