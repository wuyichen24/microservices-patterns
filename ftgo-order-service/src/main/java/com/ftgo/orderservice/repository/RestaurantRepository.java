package com.ftgo.orderservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.orderservice.model.Restaurant;

/**
 * The repository class for {@code Restaurant} entity. 
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {}
