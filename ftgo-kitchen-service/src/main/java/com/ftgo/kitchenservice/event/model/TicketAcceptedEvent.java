package com.ftgo.kitchenservice.event.model;

import java.time.LocalDateTime;

import com.ftgo.kitchenservice.event.TicketDomainEvent;

public class TicketAcceptedEvent implements TicketDomainEvent {
	public TicketAcceptedEvent(LocalDateTime readyBy) {

	}
}
