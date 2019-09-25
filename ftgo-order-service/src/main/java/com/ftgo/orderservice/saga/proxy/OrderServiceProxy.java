package com.ftgo.orderservice.saga.proxy;

import com.ftgo.orderservice.api.OrderServiceChannels;
import com.ftgo.orderservice.command.model.ApproveOrderCommand;
import com.ftgo.orderservice.command.model.RejectOrderCommand;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

public class OrderServiceProxy {
	public final CommandEndpoint<RejectOrderCommand> reject = CommandEndpointBuilder
			.forCommand(RejectOrderCommand.class)
			.withChannel(OrderServiceChannels.orderServiceChannel)
			.withReply(Success.class).build();

	public final CommandEndpoint<ApproveOrderCommand> approve = CommandEndpointBuilder
			.forCommand(ApproveOrderCommand.class)
			.withChannel(OrderServiceChannels.orderServiceChannel)
			.withReply(Success.class).build();
}