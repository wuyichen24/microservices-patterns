/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ftgo.kitchenservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftgo.kitchenservice.controller.model.GetRestaurantResponse;
import com.ftgo.kitchenservice.repository.RestaurantRepository;

import io.swagger.annotations.ApiOperation;

/**
 * The controller class for defining the external APIs about restaurants.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantServiceController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@GetMapping(path = "/{restaurantId}")
	@ApiOperation(value = "Get a restaurant by restaurant ID.", response = GetRestaurantResponse.class)
	public ResponseEntity<GetRestaurantResponse> getRestaurant(@PathVariable long restaurantId) {
		logger.debug("GET /restaurants/{restaurantId} - Get a restaurant by restaurant ID");
		
		return restaurantRepository.findById(restaurantId)
				.map(restaurant -> new ResponseEntity<>(new GetRestaurantResponse(restaurantId, restaurant.getMenuItems()), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
