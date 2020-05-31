/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ftgo.consumerservice.command;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ConsumerService consumerService;
	
	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel(ConsumerServiceChannels.CONSUMER_SERVICE_COMMAND_CHANNEL)
				.onMessage(ValidateOrderByConsumerCommand.class, this::validateOrderForConsumer).build();
	}

	private Message validateOrderForConsumer(CommandMessage<ValidateOrderByConsumerCommand> cm) {
		logger.debug("Receive ValidateOrderByConsumerCommand");
		try {
			consumerService.validateOrderForConsumer(cm.getCommand().getConsumerId(), cm.getCommand().getOrderTotal());
			return withSuccess();
		} catch (ConsumerVerificationFailedException e) {
			return withFailure();
		}
	}
}
