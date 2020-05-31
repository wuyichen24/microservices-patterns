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
package com.ftgo.deliveryservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ftgo.deliveryservice.api.model.CourierAvailability;
import com.ftgo.deliveryservice.api.model.DeliveryStatus;
import com.ftgo.deliveryservice.service.DeliveryService;

import io.swagger.annotations.ApiOperation;

/**
 * The controller class for defining the external APIs for the delivery service.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
public class DeliveryServiceController {
	@Autowired
	private DeliveryService deliveryService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostMapping(path = "/couriers/{courierId}/availability")
	@ApiOperation(value = "Update a courier availability by courier ID.", response = String.class)
	public ResponseEntity<String> updateCourierLocation(@PathVariable long courierId, @RequestBody CourierAvailability availability) {
		logger.debug("POST /couriers/{courierId}/availability - Update a courier availability by courier ID");
		
		deliveryService.updateAvailability(courierId, availability.isAvailable());
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping(path = "/deliveries/{deliveryId}")
	@ApiOperation(value = "Get delivery information by delivery ID.", response = DeliveryStatus.class)
	public ResponseEntity<DeliveryStatus> getDeliveryStatus(@PathVariable long deliveryId) {
		logger.debug("GET /deliveries/{deliveryId} - Get delivery information by delivery ID");
		
		DeliveryStatus ds = deliveryService.getDeliveryInfo(deliveryId);
		
		if (ds == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(ds, HttpStatus.OK);
		}
	}
}
