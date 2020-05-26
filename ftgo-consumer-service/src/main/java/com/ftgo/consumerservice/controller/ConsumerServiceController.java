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

	@RequestMapping(method = RequestMethod.POST)
	public CreateConsumerResponse createConsumer(@RequestBody CreateConsumerRequest request) {
		logger.debug("POST /consumers - Add a new consumer");
		
		ResultWithEvents<Consumer> result = consumerService.create(request.getName());
		return new CreateConsumerResponse(result.result.getId());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{consumerId}")
	public ResponseEntity<GetConsumerResponse> getConsumer(@PathVariable long consumerId) {
		logger.debug("GET /consumers/{consumerId} - Get a consumer by consumer ID");
		
		return consumerService.findById(consumerId)
				.map(consumer -> new ResponseEntity<>(new GetConsumerResponse(consumer.getId(), consumer.getName()), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
