package com.ftgo.orderservice.saga.createorder;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		return send(new RejectOrderCommand(data.getOrderId()))
				.to(OrderServiceChannels.orderServiceChannel).build();
	}
	
	private CommandWithDestination validateOrderByConsumer(CreateOrderSagaData data) {
		logger.info("Send ValidateOrderByConsumerCommand to consumerService channel");
		return send(new ValidateOrderByConsumerCommand(data.getOrderDetails().getConsumerId(), data.getOrderId(), data.getOrderDetails().getOrderTotal()))
				.to(ConsumerServiceChannels.consumerServiceChannel).build();
	}
	
	private CommandWithDestination createTicket(CreateOrderSagaData data) {
		logger.info("Send CreateTicketCommand to kitchenService channel");
		return send(new CreateTicketCommand(data.getOrderDetails().getRestaurantId(), data.getOrderId(), makeTicketDetails(data.getOrderDetails())))
				.to(KitchenServiceChannels.kitchenServiceChannel).build();
	}
	
	private CommandWithDestination cancelCreateTicket(CreateOrderSagaData data) {
		return send(new CancelCreateTicketCommand(data.getOrderId()))
				.to(KitchenServiceChannels.kitchenServiceChannel).build();
	}
	
	private CommandWithDestination authorize(CreateOrderSagaData data) {
		logger.info("Send AuthorizeCommand to ccountingService channel");
		return send(new AuthorizeCommand(data.getOrderDetails().getConsumerId(), data.getOrderId(), data.getOrderDetails().getOrderTotal()))
				.to(AccountingServiceChannels.accountingServiceChannel).build();
	}
	
	private CommandWithDestination confirmCreateTicket(CreateOrderSagaData data) {
		return send(new ConfirmCreateTicketCommand(data.getTicketId()))
				.to(KitchenServiceChannels.kitchenServiceChannel).build();
	}
	
	private CommandWithDestination approveOrder(CreateOrderSagaData data) {
		return send(new ApproveOrderCommand(data.getOrderId()))
				.to(OrderServiceChannels.orderServiceChannel).build();
	}
	
	private void handleCreateTicketReply(CreateOrderSagaData data, CreateTicketReply reply) {
		logger.debug("getTicketId {}", reply.getTicketId());
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
		return sagaDefinition;
	}
}
