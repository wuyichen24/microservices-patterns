package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

/**
 * The command for changing the item quantity in the ticket.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class ChangeTicketLineItemQuantityCommand implements Command {
	public ChangeTicketLineItemQuantityCommand(Long orderId) {
	}
}
