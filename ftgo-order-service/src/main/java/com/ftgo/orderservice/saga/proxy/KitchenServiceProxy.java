package com.ftgo.orderservice.saga.proxy;

import com.ftgo.kitchenservice.api.*;
import com.ftgo.kitchenservice.api.command.CancelCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

public class KitchenServiceProxy {
	public final CommandEndpoint<CreateTicketCommand> create = CommandEndpointBuilder
			.forCommand(CreateTicketCommand.class)
			.withChannel(KitchenServiceChannels.kitchenServiceChannel)
			.withReply(CreateTicketReply.class).build();

	public final CommandEndpoint<ConfirmCreateTicketCommand> confirmCreate = CommandEndpointBuilder
			.forCommand(ConfirmCreateTicketCommand.class)
			.withChannel(KitchenServiceChannels.kitchenServiceChannel)
			.withReply(Success.class).build();
	public final CommandEndpoint<CancelCreateTicketCommand> cancel = CommandEndpointBuilder
			.forCommand(CancelCreateTicketCommand.class)
			.withChannel(KitchenServiceChannels.kitchenServiceChannel)
			.withReply(Success.class).build();
}