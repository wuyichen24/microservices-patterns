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
package com.ftgo.orderservice.command;

import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftgo.common.exception.UnsupportedStateTransitionException;
import com.ftgo.orderservice.command.model.ApproveOrderCommand;
import com.ftgo.orderservice.command.model.BeginCancelCommand;
import com.ftgo.orderservice.command.model.BeginReviseOrderCommand;
import com.ftgo.orderservice.command.model.ConfirmCancelOrderCommand;
import com.ftgo.orderservice.command.model.ConfirmReviseOrderCommand;
import com.ftgo.orderservice.command.model.RejectOrderCommand;
import com.ftgo.orderservice.command.model.UndoBeginCancelCommand;
import com.ftgo.orderservice.command.model.UndoBeginReviseOrderCommand;
import com.ftgo.orderservice.controller.model.OrderRevision;
import com.ftgo.orderservice.model.BeginReviseOrderReply;
import com.ftgo.orderservice.service.OrderService;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

/**
 * The handler class for handling the command messages from other services to the order service.
 *
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderServiceCommandHandlers {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OrderService orderService;

	/**
	 * Create command handlers.
	 * 
	 * <p>Map each command to different functions to handle.
	 * 
	 * @return The {code CommandHandlers} object.
	 */
	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder
				.fromChannel("orderService")
				.onMessage(ApproveOrderCommand.class,         this::approveOrder)
				.onMessage(RejectOrderCommand.class,          this::rejectOrder)
				.onMessage(BeginCancelCommand.class,          this::beginCancel)
				.onMessage(UndoBeginCancelCommand.class,      this::undoCancel)
				.onMessage(ConfirmCancelOrderCommand.class,   this::confirmCancel)
				.onMessage(BeginReviseOrderCommand.class,     this::beginReviseOrder)
				.onMessage(UndoBeginReviseOrderCommand.class, this::undoPendingRevision)
				.onMessage(ConfirmReviseOrderCommand.class,   this::confirmRevision).build();
	}

	public Message approveOrder(CommandMessage<ApproveOrderCommand> cm) {
		logger.debug("Receive ApproveOrderCommand");
		
		long orderId = cm.getCommand().getOrderId();
		orderService.approveOrder(orderId);
		return withSuccess();
	}

	public Message rejectOrder(CommandMessage<RejectOrderCommand> cm) {
		logger.debug("Receive RejectOrderCommand");
		
		long orderId = cm.getCommand().getOrderId();
		orderService.rejectOrder(orderId);
		return withSuccess();
	}

	public Message beginCancel(CommandMessage<BeginCancelCommand> cm) {
		logger.debug("Receive BeginCancelCommand");
		
		long orderId = cm.getCommand().getOrderId();
		try {
			orderService.beginCancel(orderId);
			return withSuccess();
		} catch (UnsupportedStateTransitionException e) {
			return withFailure();
		}
	}

	public Message undoCancel(CommandMessage<UndoBeginCancelCommand> cm) {
		logger.debug("Receive UndoBeginCancelCommand");
		
		long orderId = cm.getCommand().getOrderId();
		orderService.undoCancel(orderId);
		return withSuccess();
	}

	public Message confirmCancel(CommandMessage<ConfirmCancelOrderCommand> cm) {
		logger.debug("Receive ConfirmCancelOrderCommand");
		
		long orderId = cm.getCommand().getOrderId();
		orderService.confirmCancelled(orderId);
		return withSuccess();
	}

	public Message beginReviseOrder(CommandMessage<BeginReviseOrderCommand> cm) {
		logger.debug("Receive BeginReviseOrderCommand");
		
		long          orderId  = cm.getCommand().getOrderId();
		OrderRevision revision = cm.getCommand().getRevision();
		try {
			return orderService
					.beginReviseOrder(orderId, revision)
					.map(result -> withSuccess(new BeginReviseOrderReply(result.getChange().getNewOrderTotal())))
					.orElseGet(CommandHandlerReplyBuilder::withFailure);
		} catch (UnsupportedStateTransitionException e) {
			return withFailure();
		}
	}

	public Message undoPendingRevision(CommandMessage<UndoBeginReviseOrderCommand> cm) {
		logger.debug("Receive UndoBeginReviseOrderCommand");
		
		long orderId = cm.getCommand().getOrderId();
		orderService.undoPendingRevision(orderId);
		return withSuccess();
	}

	public Message confirmRevision(CommandMessage<ConfirmReviseOrderCommand> cm) {
		logger.debug("Receive ConfirmReviseOrderCommand");
		
		long          orderId  = cm.getCommand().getOrderId();
		OrderRevision revision = cm.getCommand().getRevision();
		orderService.confirmRevision(orderId, revision);
		return withSuccess();
	}
}
