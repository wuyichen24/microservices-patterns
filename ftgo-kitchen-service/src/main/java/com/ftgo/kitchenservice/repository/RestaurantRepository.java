package com.ftgo.kitchenservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.kitchenservice.model.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
