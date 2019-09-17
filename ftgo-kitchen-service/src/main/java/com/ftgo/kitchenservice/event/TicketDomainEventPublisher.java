package com.ftgo.kitchenservice.event;

import com.ftgo.kitchenservice.model.Ticket;

import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

public class TicketDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Ticket, TicketDomainEvent> {
	public TicketDomainEventPublisher(DomainEventPublisher eventPublisher) {
		super(eventPublisher, Ticket.class, Ticket::getId);
	}
}
