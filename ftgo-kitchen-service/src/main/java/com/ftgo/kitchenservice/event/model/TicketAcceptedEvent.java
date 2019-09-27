package com.ftgo.kitchenservice.event.model;

import java.time.LocalDateTime;

import com.ftgo.kitchenservice.event.TicketDomainEvent;

public class TicketAcceptedEvent implements TicketDomainEvent {
	private LocalDateTime readyBy;
	
	public TicketAcceptedEvent(LocalDateTime readyBy) {

	}
	
	public LocalDateTime getReadyBy() {
		return readyBy;
	}
}
