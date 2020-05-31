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
package com.ftgo.consumerservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftgo.consumerservice.api.controller.model.CreateConsumerRequest;
import com.ftgo.consumerservice.api.controller.model.CreateConsumerResponse;
import com.ftgo.consumerservice.controller.model.GetConsumerResponse;
import com.ftgo.consumerservice.model.Consumer;
import com.ftgo.consumerservice.service.ConsumerService;

import io.eventuate.tram.events.publisher.ResultWithEvents;
import io.swagger.annotations.ApiOperation;

/**
 * The controller class for defining the external APIs about consumers.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(path = "/consumers")
public class ConsumerServiceController {
	@Autowired
	private ConsumerService consumerService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostMapping
	@ApiOperation(value = "Add a new consumer.", response = CreateConsumerResponse.class)
	public CreateConsumerResponse createConsumer(@RequestBody CreateConsumerRequest request) {
		logger.debug("POST /consumers - Add a new consumer");
		
		ResultWithEvents<Consumer> result = consumerService.create(request.getName());
		return new CreateConsumerResponse(result.result.getId());
	}

	@GetMapping(path = "/{consumerId}")
	@ApiOperation(value = "Get a consumer by consumer ID.", response = GetConsumerResponse.class)
	public ResponseEntity<GetConsumerResponse> getConsumer(@PathVariable long consumerId) {
		logger.debug("GET /consumers/{consumerId} - Get a consumer by consumer ID");
		
		return consumerService.findById(consumerId)
				.map(consumer -> new ResponseEntity<>(new GetConsumerResponse(consumer.getId(), consumer.getName()), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
