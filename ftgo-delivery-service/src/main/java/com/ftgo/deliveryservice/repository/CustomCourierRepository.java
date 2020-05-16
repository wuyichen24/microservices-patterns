package com.ftgo.deliveryservice.repository;

import com.ftgo.deliveryservice.model.Courier;

/**
 * The custom repository interface for {@code Courier} entity. 
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public interface CustomCourierRepository {
	Courier findOrCreateCourier(long courierId);
}
