package com.ftgo.restaurantservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.restaurantservice.model.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
