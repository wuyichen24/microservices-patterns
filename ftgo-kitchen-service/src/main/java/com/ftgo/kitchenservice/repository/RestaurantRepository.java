package com.ftgo.kitchenservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ftgo.kitchenservice.model.Restaurant;

/**
 * The repository class for {@code Restaurant} entity. 
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
