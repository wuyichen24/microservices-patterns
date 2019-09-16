package com.ftgo.consumerservice.controller;

import io.eventuate.tram.events.ResultWithEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftgo.consumerservice.api.model.CreateConsumerRequest;
import com.ftgo.consumerservice.api.model.CreateConsumerResponse;
import com.ftgo.consumerservice.controller.model.GetConsumerResponse;
import com.ftgo.consumerservice.model.Consumer;
import com.ftgo.consumerservice.service.ConsumerService;

@RestController
@RequestMapping(path = "/consumers")
public class ConsumerController {
	@Autowired
	private ConsumerService consumerService;

	@RequestMapping(method = RequestMethod.POST)
	public CreateConsumerResponse create(@RequestBody CreateConsumerRequest request) {
		ResultWithEvents<Consumer> result = consumerService.create(request.getName());
		return new CreateConsumerResponse(result.result.getId());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{consumerId}")
	public ResponseEntity<GetConsumerResponse> get(@PathVariable long consumerId) {
		return consumerService.findById(consumerId)
				.map(consumer -> new ResponseEntity<>(new GetConsumerResponse(consumer.getName()), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
