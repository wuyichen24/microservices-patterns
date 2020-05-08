package com.ftgo.deliveryservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.deliveryservice.model.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
