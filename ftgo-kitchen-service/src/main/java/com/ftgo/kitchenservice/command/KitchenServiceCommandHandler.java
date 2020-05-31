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
package com.ftgo.kitchenservice.command;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.kitchenservice.api.*;
import com.ftgo.kitchenservice.api.command.BeginCancelTicketCommand;
import com.ftgo.kitchenservice.api.command.BeginReviseTicketCommand;
import com.ftgo.kitchenservice.api.command.CancelCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCancelTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmReviseTicketCommand;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.kitchenservice.api.command.UndoBeginCancelTicketCommand;
import com.ftgo.kitchenservice.api.command.UndoBeginReviseTicketCommand;
import com.ftgo.kitchenservice.api.controller.model.CreateTicketReply;
import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.exception.RestaurantDetailsVerificationException;
import com.ftgo.kitchenservice.model.Ticket;
import com.ftgo.kitchenservice.service.KitchenService;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static io.eventuate.tram.sagas.participant.SagaReplyMessageBuilder.withLock;

/**
 * The handler class for handling the command messages from other services to the kitchen service.
 *
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public class KitchenServiceCommandHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KitchenService kitchenService;

	/**
	 * Create command handlers.
	 * 
	 * <p>Map each command to different functions to handle.
	 * 
	 * @return The {code CommandHandlers} object.
	 */
	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL)
				.onMessage(CreateTicketCommand.class,          this::createTicket)
				.onMessage(ConfirmCreateTicketCommand.class,   this::confirmCreateTicket)
				.onMessage(CancelCreateTicketCommand.class,    this::cancelCreateTicket)
				.onMessage(BeginCancelTicketCommand.class,     this::beginCancelTicket)
				.onMessage(ConfirmCancelTicketCommand.class,   this::confirmCancelTicket)
				.onMessage(UndoBeginCancelTicketCommand.class, this::undoBeginCancelTicket)
				.onMessage(BeginReviseTicketCommand.class,     this::beginReviseTicket)
				.onMessage(UndoBeginReviseTicketCommand.class, this::undoBeginReviseTicket)
				.onMessage(ConfirmReviseTicketCommand.class,   this::confirmReviseTicket).build();
	}

	private Message createTicket(CommandMessage<CreateTicketCommand> cm) {
		logger.debug("Receive CreateTicketCommand");
		
		CreateTicketCommand command = cm.getCommand();
		long restaurantId = command.getRestaurantId();
		Long ticketId = command.getOrderId();
		TicketDetails ticketDetails = command.getTicketDetails();

		try {
			Ticket ticket = kitchenService.createTicket(restaurantId, ticketId, ticketDetails);
			CreateTicketReply reply = new CreateTicketReply(ticket.getId());
			return withLock(Ticket.class, ticket.getId()).withSuccess(reply);
		} catch (RestaurantDetailsVerificationException e) {
			return withFailure();
		}
	}

	private Message confirmCreateTicket(CommandMessage<ConfirmCreateTicketCommand> cm) {
		logger.debug("Receive ConfirmCreateTicketCommand");
		
		Long ticketId = cm.getCommand().getTicketId();
		kitchenService.confirmCreateTicket(ticketId);
		return withSuccess();
	}

	private Message cancelCreateTicket(CommandMessage<CancelCreateTicketCommand> cm) {
		logger.debug("Receive CancelCreateTicketCommand");
		
		Long ticketId = cm.getCommand().getTicketId();
		kitchenService.cancelCreateTicket(ticketId);
		return withSuccess();
	}

	private Message beginCancelTicket(CommandMessage<BeginCancelTicketCommand> cm) {
		logger.debug("Receive BeginCancelTicketCommand");
		
		kitchenService.cancelTicket(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId());
		return withSuccess();
	}

	private Message confirmCancelTicket(CommandMessage<ConfirmCancelTicketCommand> cm) {
		logger.debug("Receive ConfirmCancelTicketCommand");
		
		kitchenService.confirmCancelTicket(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId());
		return withSuccess();
	}

	private Message undoBeginCancelTicket(CommandMessage<UndoBeginCancelTicketCommand> cm) {
		logger.debug("Receive UndoBeginCancelTicketCommand");
		
		kitchenService.undoCancel(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId());
		return withSuccess();
	}

	public Message beginReviseTicket(CommandMessage<BeginReviseTicketCommand> cm) {
		logger.debug("Receive BeginReviseTicketCommand");
		
		kitchenService.beginReviseOrder(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId(), cm.getCommand().getRevisedLineItemQuantities());
		return withSuccess();
	}

	public Message undoBeginReviseTicket(CommandMessage<UndoBeginReviseTicketCommand> cm) {
		logger.debug("Receive UndoBeginReviseTicketCommand");
		
		kitchenService.undoBeginReviseOrder(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId());
		return withSuccess();
	}

	public Message confirmReviseTicket(CommandMessage<ConfirmReviseTicketCommand> cm) {
		logger.debug("Receive ConfirmReviseTicketCommand");
		
		kitchenService.confirmReviseTicket(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId(), cm.getCommand().getRevisedLineItemQuantities());
		return withSuccess();
	}
}
