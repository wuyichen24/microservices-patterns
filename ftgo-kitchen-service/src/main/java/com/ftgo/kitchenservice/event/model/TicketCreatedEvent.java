package com.ftgo.kitchenservice.event.model;

import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.event.TicketDomainEvent;

/**
 * Ticket created event.
 * 
 * <p>This is one event of Ticket aggregate’s events.
 *
 * @author  Wuyi Chen
 * @date    04/14/2020
 * @version 1.0
 * @since   1.0
 */
public class TicketCreatedEvent implements TicketDomainEvent {
	public TicketCreatedEvent(Long id, TicketDetails details) {

	}
}
