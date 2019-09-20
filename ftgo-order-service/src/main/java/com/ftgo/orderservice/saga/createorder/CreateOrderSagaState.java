package com.ftgo.orderservice.saga.createorder;

import net.chrisrichardson.ftgo.orderservice.api.events.OrderDetails;
import net.chrisrichardson.ftgo.orderservice.api.events.OrderLineItem;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftgo.accountservice.api.command.AuthorizeCommand;
import com.ftgo.consumerservice.api.ValidateOrderByConsumer;
import com.ftgo.kitchenservice.api.*;
import com.ftgo.kitchenservice.api.command.CancelCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.api.model.TicketLineItem;
import com.ftgo.orderservice.command.model.ApproveOrderCommand;
import com.ftgo.orderservice.command.model.RejectOrderCommand;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CreateOrderSagaState {
	private Long         orderId;
	private long         ticketId;
	private OrderDetails orderDetails;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public CreateOrderSagaState(Long orderId, OrderDetails orderDetails) {
		this.orderId      = orderId;
		this.orderDetails = orderDetails;
	}
	
	public Long         getOrderId()               { return orderId;           }
	public OrderDetails getOrderDetails()          { return orderDetails;      }
	public void         setOrderId(Long orderId)   { this.orderId = orderId;   }
	public void         setTicketId(long ticketId) { this.ticketId = ticketId; }
	public long         getTicketId()              { return ticketId;          }

	CreateTicketCommand makeCreateTicketCommand() {
		return new CreateTicketCommand(getOrderDetails().getRestaurantId(), getOrderId(), makeTicketDetails(getOrderDetails()));
	}

	private TicketDetails makeTicketDetails(OrderDetails orderDetails) {
		// TODO FIXME
		return new TicketDetails(
				makeTicketLineItems(orderDetails.getLineItems()));
	}

	private List<TicketLineItem> makeTicketLineItems(List<OrderLineItem> lineItems) {
		return lineItems.stream().map(this::makeTicketLineItem).collect(toList());
	}

	private TicketLineItem makeTicketLineItem(OrderLineItem orderLineItem) {
		return new TicketLineItem(orderLineItem.getMenuItemId(), orderLineItem.getName(), orderLineItem.getQuantity());
	}

	void handleCreateTicketReply(CreateTicketReply reply) {
		logger.debug("getTicketId {}", reply.getTicketId());
		setTicketId(reply.getTicketId());
	}

	CancelCreateTicketCommand makeCancelCreateTicketCommand() {
		return new CancelCreateTicketCommand(getOrderId());
	}

	RejectOrderCommand makeRejectOrderCommand() {
		return new RejectOrderCommand(getOrderId());
	}

	ValidateOrderByConsumer makeValidateOrderByConsumerCommand() {
		return new ValidateOrderByConsumer(getOrderDetails().getConsumerId(), getOrderId(), getOrderDetails().getOrderTotal());
	}

	AuthorizeCommand makeAuthorizeCommand() {
		return new AuthorizeCommand(getOrderDetails().getConsumerId(), getOrderId(), getOrderDetails().getOrderTotal());
	}

	ApproveOrderCommand makeApproveOrderCommand() {
		return new ApproveOrderCommand(getOrderId());
	}

	ConfirmCreateTicketCommand makeConfirmCreateTicketCommand() {
		return new ConfirmCreateTicketCommand(getTicketId());
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
