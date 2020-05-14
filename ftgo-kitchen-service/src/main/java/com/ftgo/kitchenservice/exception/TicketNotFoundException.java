package com.ftgo.kitchenservice.exception;

/**
 * The exception to indicate that the ticket can not be found by ID.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class TicketNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TicketNotFoundException(long orderId) {
		super("Ticket not found: " + orderId);
	}
}
