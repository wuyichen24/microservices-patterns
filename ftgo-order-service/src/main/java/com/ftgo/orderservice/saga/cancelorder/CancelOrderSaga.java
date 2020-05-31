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
package com.ftgo.orderservice.saga.cancelorder;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ftgo.accountservice.api.AccountingServiceChannels;
import com.ftgo.accountservice.api.command.ReverseAuthorizationCommand;
import com.ftgo.kitchenservice.api.KitchenServiceChannels;
import com.ftgo.kitchenservice.api.command.BeginCancelTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCancelTicketCommand;
import com.ftgo.kitchenservice.api.command.UndoBeginCancelTicketCommand;
import com.ftgo.orderservice.api.OrderServiceChannels;
import com.ftgo.orderservice.command.model.BeginCancelCommand;
import com.ftgo.orderservice.command.model.ConfirmCancelOrderCommand;
import com.ftgo.orderservice.command.model.UndoBeginCancelCommand;

import javax.annotation.PostConstruct;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

/**
 * A singleton class that defines the saga’s state machine for canceling an order.
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public class CancelOrderSaga implements SimpleSaga<CancelOrderSagaData> {
	private SagaDefinition<CancelOrderSagaData> sagaDefinition;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostConstruct
	public void initializeSagaDefinition() {
		sagaDefinition = step().invokeParticipant(this::beginCancel).withCompensation(this::undoBeginCancel)
				.step().invokeParticipant(this::beginCancelTicket).withCompensation(this::undoBeginCancelTicket)
				.step().invokeParticipant(this::reverseAuthorization)
				.step().invokeParticipant(this::confirmTicketCancel)
				.step().invokeParticipant(this::confirmOrderCancel)
				.build();

	}

	private CommandWithDestination beginCancel(CancelOrderSagaData data) {
		logger.debug("Send BeginCancelCommand to orderService channel");
		return send(new BeginCancelCommand(data.getRestaurantId(), data.getOrderId()))
				.to(OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination undoBeginCancel(CancelOrderSagaData data) {
		logger.debug("Send UndoBeginCancelCommand to orderService channel");
		return send(new UndoBeginCancelCommand(data.getOrderId()))
				.to(OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination beginCancelTicket(CancelOrderSagaData data) {
		logger.debug("Send BeginCancelTicketCommand to kitchenService channel");
		return send(new BeginCancelTicketCommand(data.getRestaurantId(),(long) data.getOrderId()))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination undoBeginCancelTicket(CancelOrderSagaData data) {
		logger.debug("Send UndoBeginCancelTicketCommand to kitchenService channel");
		return send(new UndoBeginCancelTicketCommand(data.getRestaurantId(), data.getOrderId()))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination reverseAuthorization(CancelOrderSagaData data) {
		logger.debug("Send ReverseAuthorizationCommand to accountingService channel");
		return send(new ReverseAuthorizationCommand(data.getConsumerId(), data.getOrderId(), data.getOrderTotal()))
				.to(AccountingServiceChannels.ACCOUNTING_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination confirmTicketCancel(CancelOrderSagaData data) {
		logger.debug("Send ConfirmCancelTicketCommand to kitchenService channel");
		return send(new ConfirmCancelTicketCommand(data.getRestaurantId(), data.getOrderId()))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination confirmOrderCancel(CancelOrderSagaData data) {
		logger.debug("Send ConfirmCancelOrderCommand to orderService channel");
		return send(new ConfirmCancelOrderCommand(data.getOrderId()))
				.to(OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL).build();
	}

	@Override
	public SagaDefinition<CancelOrderSagaData> getSagaDefinition() {
		Assert.notNull(sagaDefinition, "sagaDefinition is null.");
		return sagaDefinition;
	}
}
