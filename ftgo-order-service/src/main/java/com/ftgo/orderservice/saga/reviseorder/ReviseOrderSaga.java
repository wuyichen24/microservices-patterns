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
package com.ftgo.orderservice.saga.reviseorder;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

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

/**
 * A singleton class that defines the saga process for revising an order.
 *
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
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
		logger.debug("Send BeginReviseOrderCommand to orderService channel");
		return send(new BeginReviseOrderCommand(data.getOrderId(), data.getOrderRevision()))
				.to(OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination undoBeginReviseOrder(ReviseOrderSagaData data) {
		logger.debug("Send UndoBeginReviseOrderCommand to orderService channel");
		return send(new UndoBeginReviseOrderCommand(data.getOrderId()))
				.to(OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination beginReviseTicket(ReviseOrderSagaData data) {
		logger.debug("Send BeginReviseTicketCommand to kitchenService channel");
		return send(new BeginReviseTicketCommand(data.getRestaurantId(), data.getOrderId(), data.getOrderRevision().getRevisedLineItemQuantities()))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination undoBeginReviseTicket(ReviseOrderSagaData data) {
		logger.debug("Send UndoBeginReviseTicketCommand to kitchenService channel");
		return send(new UndoBeginReviseTicketCommand(data.getRestaurantId(), data.getOrderId()))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination reviseAuthorization(ReviseOrderSagaData data) {
		logger.debug("Send ReviseAuthorizationCommand to accountingService channel");
		return send(new ReviseAuthorizationCommand(data.getConsumerId(),data.getOrderId(), data.getRevisedOrderTotal()))
				.to(AccountingServiceChannels.ACCOUNTING_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination confirmTicketRevision(ReviseOrderSagaData data) {
		logger.debug("Send ConfirmReviseTicketCommand to kitchenService channel");
		return send(new ConfirmReviseTicketCommand(data.getRestaurantId(), data.getOrderId(), data.getOrderRevision().getRevisedLineItemQuantities()))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination confirmOrderRevision(ReviseOrderSagaData data) {
		logger.debug("Send ConfirmReviseOrderCommand to orderService channel");
		return send(new ConfirmReviseOrderCommand(data.getOrderId(), data.getOrderRevision()))
				.to(OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private void handleBeginReviseOrderReply(ReviseOrderSagaData data, BeginReviseOrderReply reply) {
		logger.info("Receive BeginReviseOrderReply {}", reply.getRevisedOrderTotal());
		data.setRevisedOrderTotal(reply.getRevisedOrderTotal());
	}
	
	@Override
	public SagaDefinition<ReviseOrderSagaData> getSagaDefinition() {
		Assert.notNull(sagaDefinition, "sagaDefinition is null.");
		return sagaDefinition;
	}
}
