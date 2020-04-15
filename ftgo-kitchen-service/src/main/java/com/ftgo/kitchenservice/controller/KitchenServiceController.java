package com.ftgo.kitchenservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftgo.kitchenservice.controller.model.GetRestaurantResponse;
import com.ftgo.kitchenservice.repository.RestaurantRepository;

/**
 * The controller class for defining the external APIs of kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    04/14/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(path = "/restaurants")
public class KitchenServiceController {
	@Autowired
	private RestaurantRepository restaurantRepository;

	@RequestMapping(path = "/{restaurantId}", method = RequestMethod.GET)
	public ResponseEntity<GetRestaurantResponse> getRestaurant(@PathVariable long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.map(restaurant -> new ResponseEntity<>(new GetRestaurantResponse(restaurantId), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
