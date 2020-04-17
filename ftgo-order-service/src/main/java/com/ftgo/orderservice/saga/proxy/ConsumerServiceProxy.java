package com.ftgo.orderservice.saga.proxy;

import com.ftgo.consumerservice.api.ConsumerServiceChannels;
import com.ftgo.consumerservice.api.command.ValidateOrderByConsumerCommand;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

public class ConsumerServiceProxy {
	public final CommandEndpoint<ValidateOrderByConsumerCommand> validateOrder = CommandEndpointBuilder
			.forCommand(ValidateOrderByConsumerCommand.class)
			.withChannel(ConsumerServiceChannels.consumerServiceChannel)
			.withReply(Success.class).build();
}
