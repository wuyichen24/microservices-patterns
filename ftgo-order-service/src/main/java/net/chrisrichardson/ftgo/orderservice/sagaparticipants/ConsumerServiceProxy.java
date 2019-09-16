package net.chrisrichardson.ftgo.orderservice.sagaparticipants;

import com.ftgo.consumerservice.api.ConsumerServiceChannels;
import com.ftgo.consumerservice.api.ValidateOrderByConsumer;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

public class ConsumerServiceProxy {
	public final CommandEndpoint<ValidateOrderByConsumer> validateOrder = CommandEndpointBuilder
			.forCommand(ValidateOrderByConsumer.class)
			.withChannel(ConsumerServiceChannels.consumerServiceChannel)
			.withReply(Success.class).build();
}
