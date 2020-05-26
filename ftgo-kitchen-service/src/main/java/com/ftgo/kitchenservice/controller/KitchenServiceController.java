package com.ftgo.kitchenservice.controller;

import com.ftgo.kitchenservice.api.controller.model.TicketAcceptance;
import com.ftgo.kitchenservice.service.KitchenService;

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

	@RequestMapping(path="/tickets/{ticketId}/accept", method= RequestMethod.POST)
	public ResponseEntity<String> acceptTicket(@PathVariable long ticketId, @RequestBody TicketAcceptance ticketAcceptance) {
		logger.debug("POST /tickets/{ticketId}/accept - Accept a ticket by ticket ID");
		
		kitchenService.accept(ticketId, ticketAcceptance.getReadyBy());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
