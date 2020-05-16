package com.ftgo.deliveryservice.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.deliveryservice.model.Courier;

import javax.persistence.EntityManager;

/**
 * The custom repository inplementation for {@code Courier} entity. 
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class CustomCourierRepositoryImpl implements CustomCourierRepository {
	@Autowired
	private EntityManager entityManager;

	@Override
	public Courier findOrCreateCourier(long courierId) {
		Courier courier = entityManager.find(Courier.class, courierId);
		if (courier == null) {
			courier = Courier.create(courierId);
			entityManager.persist(courier);
		}
		return courier;
	}
}
