package com.ftgo.orderservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.orderservice.model.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
