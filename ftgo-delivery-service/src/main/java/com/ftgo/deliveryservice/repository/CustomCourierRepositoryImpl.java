package com.ftgo.deliveryservice.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.deliveryservice.model.Courier;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomCourierRepositoryImpl implements CustomCourierRepository {

	@Autowired
	private EntityManager entityManager;

	// @Override
	// public List<Courier> findAllAvailable() {
	// return entityManager.createQuery("").getResultList();
	// }

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
