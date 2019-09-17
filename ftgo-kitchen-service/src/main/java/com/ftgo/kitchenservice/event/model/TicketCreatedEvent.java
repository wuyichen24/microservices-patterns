package com.ftgo.kitchenservice.event.model;

import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.event.TicketDomainEvent;

public class TicketCreatedEvent implements TicketDomainEvent {
	public TicketCreatedEvent(Long id, TicketDetails details) {

	}
}
