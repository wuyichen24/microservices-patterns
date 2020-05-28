package com.ftgo.restaurantservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftgo.restaurantservice.api.controller.model.CreateRestaurantRequest;
import com.ftgo.restaurantservice.controller.model.CreateRestaurantResponse;
import com.ftgo.restaurantservice.controller.model.GetRestaurantResponse;
import com.ftgo.restaurantservice.model.Restaurant;
import com.ftgo.restaurantservice.service.RestaurantService;

import io.swagger.annotations.ApiOperation;

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
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RestaurantService restaurantService;

	@PostMapping
	@ApiOperation(value = "Add a new restaurant.", response = CreateRestaurantResponse.class)
	public CreateRestaurantResponse create(@RequestBody CreateRestaurantRequest request) {
		logger.debug("POST /restaurants - Add a new restaurant");
		
		Restaurant r = restaurantService.create(request);
		return new CreateRestaurantResponse(r.getId());
	}

	@GetMapping(path = "/{restaurantId}")
	@ApiOperation(value = "Get a restaurant by restaurant ID.", response = GetRestaurantResponse.class)
	public ResponseEntity<GetRestaurantResponse> get(@PathVariable long restaurantId) {
		logger.debug("GET /restaurants/{restaurantId} - Get a restaurant by restaurant ID");
		
		return restaurantService
				.findById(restaurantId)
				.map(r -> new ResponseEntity<>(makeGetRestaurantResponse(r), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	private GetRestaurantResponse makeGetRestaurantResponse(Restaurant r) {
		return new GetRestaurantResponse(r.getId(), r.getName(), r.getMenu());
	}
}
