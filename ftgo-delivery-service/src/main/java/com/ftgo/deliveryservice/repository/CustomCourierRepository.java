package com.ftgo.deliveryservice.repository;

import org.springframework.data.jpa.repository.Query;

import com.ftgo.deliveryservice.model.Courier;

import java.util.List;

public interface CustomCourierRepository {

	Courier findOrCreateCourier(long courierId);

}
