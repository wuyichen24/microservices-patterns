package com.ftgo.kitchenservice.command;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

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
import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.exception.RestaurantDetailsVerificationException;
import com.ftgo.kitchenservice.model.Ticket;
import com.ftgo.kitchenservice.service.KitchenService;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static io.eventuate.tram.sagas.participant.SagaReplyMessageBuilder.withLock;

public class KitchenServiceCommandHandler {
	@Autowired
	private KitchenService kitchenService;

	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel(KitchenServiceChannels.kitchenServiceChannel)
				.onMessage(CreateTicketCommand.class, this::createTicket)
				.onMessage(ConfirmCreateTicketCommand.class, this::confirmCreateTicket)
				.onMessage(CancelCreateTicketCommand.class, this::cancelCreateTicket)

				.onMessage(BeginCancelTicketCommand.class, this::beginCancelTicket)
				.onMessage(ConfirmCancelTicketCommand.class, this::confirmCancelTicket)
				.onMessage(UndoBeginCancelTicketCommand.class, this::undoBeginCancelTicket)

				.onMessage(BeginReviseTicketCommand.class, this::beginReviseTicket)
				.onMessage(UndoBeginReviseTicketCommand.class, this::undoBeginReviseTicket)
				.onMessage(ConfirmReviseTicketCommand.class, this::confirmReviseTicket).build();
	}

	private Message createTicket(CommandMessage<CreateTicketCommand> cm) {
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
		Long ticketId = cm.getCommand().getTicketId();
		kitchenService.confirmCreateTicket(ticketId);
		return withSuccess();
	}

	private Message cancelCreateTicket(CommandMessage<CancelCreateTicketCommand> cm) {
		Long ticketId = cm.getCommand().getTicketId();
		kitchenService.cancelCreateTicket(ticketId);
		return withSuccess();
	}

	private Message beginCancelTicket(CommandMessage<BeginCancelTicketCommand> cm) {
		kitchenService.cancelTicket(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId());
		return withSuccess();
	}

	private Message confirmCancelTicket(CommandMessage<ConfirmCancelTicketCommand> cm) {
		kitchenService.confirmCancelTicket(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId());
		return withSuccess();
	}

	private Message undoBeginCancelTicket(CommandMessage<UndoBeginCancelTicketCommand> cm) {
		kitchenService.undoCancel(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId());
		return withSuccess();
	}

	public Message beginReviseTicket(CommandMessage<BeginReviseTicketCommand> cm) {
		kitchenService.beginReviseOrder(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId(),
				cm.getCommand().getRevisedLineItemQuantities());
		return withSuccess();
	}

	public Message undoBeginReviseTicket(CommandMessage<UndoBeginReviseTicketCommand> cm) {
		kitchenService.undoBeginReviseOrder(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId());
		return withSuccess();
	}

	public Message confirmReviseTicket(CommandMessage<ConfirmReviseTicketCommand> cm) {
		kitchenService.confirmReviseTicket(cm.getCommand().getRestaurantId(), cm.getCommand().getOrderId(),
				cm.getCommand().getRevisedLineItemQuantities());
		return withSuccess();
	}
}
