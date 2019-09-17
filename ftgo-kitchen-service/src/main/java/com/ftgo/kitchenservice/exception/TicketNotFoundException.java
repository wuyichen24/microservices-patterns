package com.ftgo.kitchenservice.exception;

public class TicketNotFoundException extends RuntimeException {
	public TicketNotFoundException(long orderId) {
		super("Ticket not found: " + orderId);
	}
}
