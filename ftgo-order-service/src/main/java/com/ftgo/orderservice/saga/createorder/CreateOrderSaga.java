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
package com.ftgo.orderservice.saga.createorder;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ftgo.consumerservice.api.command.ValidateOrderByConsumerCommand;
import com.ftgo.kitchenservice.api.KitchenServiceChannels;
import com.ftgo.kitchenservice.api.command.CancelCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.kitchenservice.api.controller.model.CreateTicketReply;
import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.api.model.TicketLineItem;
import com.ftgo.orderservice.api.OrderServiceChannels;
import com.ftgo.orderservice.api.model.OrderDetails;
import com.ftgo.orderservice.api.model.OrderLineItem;
import com.ftgo.accountservice.api.AccountingServiceChannels;
import com.ftgo.accountservice.api.command.AuthorizeCommand;
import com.ftgo.consumerservice.api.ConsumerServiceChannels;
import com.ftgo.orderservice.command.model.ApproveOrderCommand;
import com.ftgo.orderservice.command.model.RejectOrderCommand;

/**
 * A singleton class that defines the saga process for creating an order.
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {
	private SagaDefinition<CreateOrderSagaData> sagaDefinition;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
     * Construct a {@code CreateOrderSaga}.
     * 
     * <p>It defines the steps of the sage for creating a order.
     */
	public CreateOrderSaga() {
		this.sagaDefinition = step().withCompensation(this::rejectOrder)
				.step().invokeParticipant(this::validateOrderByConsumer)
				.step().invokeParticipant(this::createTicket).onReply(CreateTicketReply.class, this::handleCreateTicketReply).withCompensation(this::cancelCreateTicket)
				.step().invokeParticipant(this::authorize)
				.step().invokeParticipant(this::confirmCreateTicket)
				.step().invokeParticipant(this::approveOrder)
				.build();
	}
	
	private CommandWithDestination rejectOrder(CreateOrderSagaData data) {
		logger.debug("Send RejectOrderCommand to orderService channel");
		return send(new RejectOrderCommand(data.getOrderId()))
				.to(OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination validateOrderByConsumer(CreateOrderSagaData data) {
		logger.debug("Send ValidateOrderByConsumerCommand to consumerService channel");
		return send(new ValidateOrderByConsumerCommand(data.getOrderDetails().getConsumerId(), data.getOrderId(), data.getOrderDetails().getOrderTotal()))
				.to(ConsumerServiceChannels.CONSUMER_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination createTicket(CreateOrderSagaData data) {
		logger.debug("Send CreateTicketCommand to kitchenService channel");
		return send(new CreateTicketCommand(data.getOrderDetails().getRestaurantId(), data.getOrderId(), makeTicketDetails(data.getOrderDetails())))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination cancelCreateTicket(CreateOrderSagaData data) {
		logger.debug("Send CancelCreateTicketCommand to kitchenService channel");
		return send(new CancelCreateTicketCommand(data.getOrderId()))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination authorize(CreateOrderSagaData data) {
		logger.debug("Send AuthorizeCommand to accountingService channel");
		return send(new AuthorizeCommand(data.getOrderDetails().getConsumerId(), data.getOrderId(), data.getOrderDetails().getOrderTotal()))
				.to(AccountingServiceChannels.ACCOUNTING_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination confirmCreateTicket(CreateOrderSagaData data) {
		logger.debug("Send ConfirmCreateTicketCommand to kitchenService channel");
		return send(new ConfirmCreateTicketCommand(data.getTicketId()))
				.to(KitchenServiceChannels.KITCHEN_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private CommandWithDestination approveOrder(CreateOrderSagaData data) {
		logger.debug("Send ApproveOrderCommand to orderService channel");
		return send(new ApproveOrderCommand(data.getOrderId()))
				.to(OrderServiceChannels.ORDER_SERVICE_COMMAND_CHANNEL).build();
	}
	
	private void handleCreateTicketReply(CreateOrderSagaData data, CreateTicketReply reply) {
		logger.debug("Receive CreateTicketReply {}", reply.getTicketId());
		data.setTicketId(reply.getTicketId());
	}

	private TicketDetails makeTicketDetails(OrderDetails orderDetails) {
		return new TicketDetails(makeTicketLineItems(orderDetails.getLineItems()));
	}
	
	private List<TicketLineItem> makeTicketLineItems(List<OrderLineItem> lineItems) {
		return lineItems.stream().map(this::makeTicketLineItem).collect(toList());
	}
	
	private TicketLineItem makeTicketLineItem(OrderLineItem orderLineItem) {
		return new TicketLineItem(orderLineItem.getMenuItemId(), orderLineItem.getName(), orderLineItem.getQuantity());
	}

	@Override
	public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
		Assert.notNull(sagaDefinition, "sagaDefinition is null.");
		return sagaDefinition;
	}
}
