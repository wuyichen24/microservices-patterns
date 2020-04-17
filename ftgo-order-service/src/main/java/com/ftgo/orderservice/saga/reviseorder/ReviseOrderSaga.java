package com.ftgo.orderservice.saga.reviseorder;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftgo.accountservice.api.AccountingServiceChannels;
import com.ftgo.accountservice.api.command.ReviseAuthorizationCommand;
import com.ftgo.kitchenservice.api.KitchenServiceChannels;
import com.ftgo.kitchenservice.api.command.BeginReviseTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmReviseTicketCommand;
import com.ftgo.kitchenservice.api.command.UndoBeginReviseTicketCommand;
import com.ftgo.orderservice.api.OrderServiceChannels;
import com.ftgo.orderservice.command.model.BeginReviseOrderCommand;
import com.ftgo.orderservice.command.model.ConfirmReviseOrderCommand;
import com.ftgo.orderservice.command.model.UndoBeginReviseOrderCommand;
import com.ftgo.orderservice.model.BeginReviseOrderReply;

import javax.annotation.PostConstruct;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

public class ReviseOrderSaga implements SimpleSaga<ReviseOrderSagaData> {
	private SagaDefinition<ReviseOrderSagaData> sagaDefinition;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostConstruct
	public void initializeSagaDefinition() {
		sagaDefinition = step().invokeParticipant(this::beginReviseOrder).onReply(BeginReviseOrderReply.class, this::handleBeginReviseOrderReply).withCompensation(this::undoBeginReviseOrder)
				.step().invokeParticipant(this::beginReviseTicket).withCompensation(this::undoBeginReviseTicket)
				.step().invokeParticipant(this::reviseAuthorization)
				.step().invokeParticipant(this::confirmTicketRevision)
				.step().invokeParticipant(this::confirmOrderRevision)
				.build();
	}
	
	private CommandWithDestination beginReviseOrder(ReviseOrderSagaData data) {
		return send(new BeginReviseOrderCommand(data.getOrderId(), data.getOrderRevision()))
				.to(OrderServiceChannels.orderServiceChannel).build();
	}
	
	private CommandWithDestination undoBeginReviseOrder(ReviseOrderSagaData data) {
		return send(new UndoBeginReviseOrderCommand(data.getOrderId()))
				.to(OrderServiceChannels.orderServiceChannel).build();
	}
	
	private CommandWithDestination beginReviseTicket(ReviseOrderSagaData data) {
		return send(new BeginReviseTicketCommand(data.getRestaurantId(), data.getOrderId(), data.getOrderRevision().getRevisedLineItemQuantities()))
				.to(KitchenServiceChannels.kitchenServiceChannel).build();
	}
	
	private CommandWithDestination undoBeginReviseTicket(ReviseOrderSagaData data) {
		return send(new UndoBeginReviseTicketCommand(data.getRestaurantId(), data.getOrderId()))
				.to(KitchenServiceChannels.kitchenServiceChannel).build();
	}
	
	private CommandWithDestination reviseAuthorization(ReviseOrderSagaData data) {
		return send(new ReviseAuthorizationCommand(data.getConsumerId(),data.getOrderId(), data.getRevisedOrderTotal()))
				.to(AccountingServiceChannels.accountingServiceChannel).build();
	}
	
	private CommandWithDestination confirmTicketRevision(ReviseOrderSagaData data) {
		return send(new ConfirmReviseTicketCommand(data.getRestaurantId(), data.getOrderId(), data.getOrderRevision().getRevisedLineItemQuantities()))
				.to(KitchenServiceChannels.kitchenServiceChannel).build();
	}
	
	private CommandWithDestination confirmOrderRevision(ReviseOrderSagaData data) {
		return send(new ConfirmReviseOrderCommand(data.getOrderId(), data.getOrderRevision()))
				.to(OrderServiceChannels.orderServiceChannel).build();
	}
	
	private void handleBeginReviseOrderReply(ReviseOrderSagaData data, BeginReviseOrderReply reply) {
		logger.info("ƒ order total: {}", reply.getRevisedOrderTotal());
		data.setRevisedOrderTotal(reply.getRevisedOrderTotal());
	}
	
	@Override
	public SagaDefinition<ReviseOrderSagaData> getSagaDefinition() {
		return sagaDefinition;
	}
}
