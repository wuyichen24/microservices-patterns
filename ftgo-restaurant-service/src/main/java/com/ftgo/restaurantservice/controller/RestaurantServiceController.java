package com.ftgo.restaurantservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftgo.restaurantservice.api.controller.model.CreateRestaurantRequest;
import com.ftgo.restaurantservice.controller.model.CreateRestaurantResponse;
import com.ftgo.restaurantservice.controller.model.GetRestaurantResponse;
import com.ftgo.restaurantservice.model.Restaurant;
import com.ftgo.restaurantservice.service.RestaurantService;

/**
 * The controller class for defining the external APIs about restaurants.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantServiceController {
	@Autowired
	private RestaurantService restaurantService;

	@RequestMapping(method = RequestMethod.POST)
	public CreateRestaurantResponse create(@RequestBody CreateRestaurantRequest request) {
		Restaurant r = restaurantService.create(request);
		return new CreateRestaurantResponse(r.getId());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{restaurantId}")
	public ResponseEntity<GetRestaurantResponse> get(@PathVariable long restaurantId) {
		return restaurantService
				.findById(restaurantId)
				.map(r -> new ResponseEntity<>(makeGetRestaurantResponse(r), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	private GetRestaurantResponse makeGetRestaurantResponse(Restaurant r) {
		return new GetRestaurantResponse(r.getId(), r.getName());
	}
}
