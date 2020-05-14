package com.ftgo.kitchenservice.event.model;

import java.time.LocalDateTime;

import com.ftgo.kitchenservice.event.TicketDomainEvent;

/**
 * Ticket accepted event.
 *
 * @author  Wuyi Chen
 * @date    04/14/2020
 * @version 1.0
 * @since   1.0
 */
public class TicketAcceptedEvent implements TicketDomainEvent {
	private LocalDateTime readyBy;        // the estimate of when the order will be ready for pickup. 
	
	public TicketAcceptedEvent(LocalDateTime readyBy) {

	}
	
	public LocalDateTime getReadyBy() {
		return readyBy;
	}
}
