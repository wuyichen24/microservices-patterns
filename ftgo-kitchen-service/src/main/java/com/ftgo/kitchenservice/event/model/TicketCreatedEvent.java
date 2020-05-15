package com.ftgo.kitchenservice.event.model;

import com.ftgo.kitchenservice.api.model.TicketDetails;

/**
 * Ticket created event.
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
