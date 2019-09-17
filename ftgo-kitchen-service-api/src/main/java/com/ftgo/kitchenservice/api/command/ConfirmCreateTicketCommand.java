package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

public class ConfirmCreateTicketCommand implements Command {
	private Long ticketId;

	public ConfirmCreateTicketCommand(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getTicketId()              { return ticketId;          }
	public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
}
