package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

/**
 * The command for canceling a created ticket.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class CancelCreateTicketCommand implements Command {
	private Long ticketId;

	public CancelCreateTicketCommand(long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getTicketId()              { return ticketId;          }
	public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
}
