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

import com.ftgo.kitchenservice.api.controller.model.TicketAcceptance;
import com.ftgo.kitchenservice.service.KitchenService;

import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The controller class for defining the external APIs about tickets.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
public class KitchenServiceController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private KitchenService kitchenService;

	public KitchenServiceController(KitchenService kitchenService) {
		this.kitchenService = kitchenService;
	}

	@PostMapping(path="/tickets/{ticketId}/accept")
	@ApiOperation(value = "Accept a ticket by ticket ID.", response = String.class)
	public ResponseEntity<String> acceptTicket(@PathVariable long ticketId, @RequestBody TicketAcceptance ticketAcceptance) {
		logger.debug("POST /tickets/{ticketId}/accept - Accept a ticket by ticket ID");
		
		kitchenService.accept(ticketId, ticketAcceptance.getReadyBy());
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
}
