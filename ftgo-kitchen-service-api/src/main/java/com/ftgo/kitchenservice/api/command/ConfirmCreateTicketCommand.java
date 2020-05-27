package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

/**
 * The command for confirming the ticket has been created.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class ConfirmCreateTicketCommand implements Command {
	private Long ticketId;

	public ConfirmCreateTicketCommand() {}
	
	public ConfirmCreateTicketCommand(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getTicketId()              { return ticketId;          }
	public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
}
