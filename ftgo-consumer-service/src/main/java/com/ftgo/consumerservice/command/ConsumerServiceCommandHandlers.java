package com.ftgo.consumerservice.command;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.consumerservice.api.ConsumerServiceChannels;
import com.ftgo.consumerservice.api.command.ValidateOrderByConsumerCommand;
import com.ftgo.consumerservice.exception.ConsumerVerificationFailedException;
import com.ftgo.consumerservice.service.ConsumerService;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

/**
 * The handler class for handling the command messages from other services to the consumer service.
 *
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class ConsumerServiceCommandHandlers {
	@Autowired
	private ConsumerService consumerService;

	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel(ConsumerServiceChannels.consumerServiceChannel)
				.onMessage(ValidateOrderByConsumerCommand.class, this::validateOrderForConsumer).build();
	}

	private Message validateOrderForConsumer(CommandMessage<ValidateOrderByConsumerCommand> cm) {
		try {
			consumerService.validateOrderForConsumer(cm.getCommand().getConsumerId(), cm.getCommand().getOrderTotal());
			return withSuccess();
		} catch (ConsumerVerificationFailedException e) {
			return withFailure();
		}
	}
}
