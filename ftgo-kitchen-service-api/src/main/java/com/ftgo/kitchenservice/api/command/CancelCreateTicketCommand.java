package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

public class CancelCreateTicketCommand implements Command {
	private Long ticketId;

	public CancelCreateTicketCommand(long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getTicketId()              { return ticketId;          }
	public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
}
