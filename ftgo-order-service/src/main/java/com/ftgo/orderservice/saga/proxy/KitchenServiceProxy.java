package com.ftgo.orderservice.saga.proxy;

import com.ftgo.kitchenservice.api.*;
import com.ftgo.kitchenservice.api.command.CancelCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.kitchenservice.api.controller.model.CreateTicketReply;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

/**
 * The kitchen service proxy class defines the kitchen service’s messaging APIs (command message endpoints).
 * 
 * <p>There are 3 commands:
 * <ul>
 *    <li>Creates a Ticket.
 *    <li>Confirms the ticket creation.
 *    <li>Cancels a Ticket.
 * </ul>
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public class KitchenServiceProxy {
	/**
	 * Endpoint 1: create - Creates a Ticket.
	 */
	public final CommandEndpoint<CreateTicketCommand> create = CommandEndpointBuilder
			.forCommand(CreateTicketCommand.class)                         // the command type
			.withChannel(KitchenServiceChannels.kitchenServiceChannel)     // the command message’s destination channel
			.withReply(CreateTicketReply.class).build();                   // the expected reply types.

	/**
	 * Endpoint 2: confirmCreate - Confirms the ticket creation.
	 */
	public final CommandEndpoint<ConfirmCreateTicketCommand> confirmCreate = CommandEndpointBuilder
			.forCommand(ConfirmCreateTicketCommand.class)
			.withChannel(KitchenServiceChannels.kitchenServiceChannel)
			.withReply(Success.class).build();
	
	/**
	 * Endpoint 3: cancel— Cancels a Ticket.
	 */
	public final CommandEndpoint<CancelCreateTicketCommand> cancel = CommandEndpointBuilder
			.forCommand(CancelCreateTicketCommand.class)
			.withChannel(KitchenServiceChannels.kitchenServiceChannel)
			.withReply(Success.class).build();
}