package com.ftgo.kitchenservice.event;

import com.ftgo.kitchenservice.event.model.TicketDomainEvent;
import com.ftgo.kitchenservice.model.Ticket;

import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

/**
 * The class for publishing Ticket aggregates’ domain events.
 * 
 * <p>This class only publishes events that are a subclass of {@code TicketDomainEvent}.
 *
 * @author  Wuyi Chen
 * @date    04/14/2020
 * @version 1.0
 * @since   1.0
 */
public class TicketDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Ticket, TicketDomainEvent> {
	public TicketDomainEventPublisher(DomainEventPublisher eventPublisher) {
		super(eventPublisher, Ticket.class, Ticket::getId);
	}
}
