package com.ftgo.deliveryservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.deliveryservice.model.Delivery;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
}
