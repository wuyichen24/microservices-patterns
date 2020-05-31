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
package com.ftgo.orderservice.model;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;

import javax.persistence.*;

import com.ftgo.common.exception.UnsupportedStateTransitionException;
import com.ftgo.common.model.Money;
import com.ftgo.orderservice.api.event.*;
import com.ftgo.orderservice.api.model.OrderDetails;
import com.ftgo.orderservice.api.model.OrderLineItem;
import com.ftgo.orderservice.api.model.OrderState;
import com.ftgo.orderservice.controller.model.OrderRevision;
import com.ftgo.orderservice.event.model.OrderAuthorizedEvent;
import com.ftgo.orderservice.event.model.OrderCancelledEvent;
import com.ftgo.orderservice.event.model.OrderRejectedEvent;
import com.ftgo.orderservice.event.model.OrderRevisedEvent;
import com.ftgo.orderservice.event.model.OrderRevisionProposedEvent;
import com.ftgo.orderservice.exception.OrderMinimumNotMetException;

import java.util.List;

import static com.ftgo.orderservice.api.model.OrderState.APPROVAL_PENDING;
import static com.ftgo.orderservice.api.model.OrderState.APPROVED;
import static com.ftgo.orderservice.api.model.OrderState.REJECTED;
import static com.ftgo.orderservice.api.model.OrderState.REVISION_PENDING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * The entity class for orders.
 * 
 * <p>This class is the root of the Order aggregate.
 * 
 * @author  Wuyi Chen
 * @date    04/16/2020
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
public class Order {
	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Long version;

	@Enumerated(EnumType.STRING)
	private OrderState state;

	private Long consumerId;
	private Long restaurantId;

	@Embedded
	private OrderLineItems orderLineItems;

	@Embedded
	private DeliveryInformation deliveryInformation;

	@Embedded
	private PaymentInformation paymentInformation;

	@Embedded
	private Money orderMinimum = new Money(Integer.MAX_VALUE);

	private Order() {}
	
	/**
	 * Create a new order
	 * 
	 * @param  consumerId
	 *         The consumer ID.
	 *         
	 * @param  restaurantId
	 *         The restaurant ID.
	 *         
	 * @param  deliveryInformation
	 *         The delivery information.
	 *         
	 * @param  orderLineItems
	 *         The items in this order.
	 */
	public Order(long consumerId, long restaurantId, DeliveryInformation deliveryInformation, List<OrderLineItem> orderLineItems) {
	    this.consumerId          = consumerId;
	    this.restaurantId        = restaurantId;
	    this.deliveryInformation = deliveryInformation;
	    this.orderLineItems      = new OrderLineItems(orderLineItems);
	    this.state               = APPROVAL_PENDING;
	}

	public Long                getId()                  { return id;                  }
	public void                setId(Long id)           { this.id = id;               }
	public Long                getVersion()             { return version;             }
	public OrderState          getState()               { return state;               }
	public long                getRestaurantId()        { return restaurantId;        }
	public Long                getConsumerId()          { return consumerId;          }
	public DeliveryInformation getDeliveryInformation() { return deliveryInformation; }
	
	public List<OrderLineItem> getLineItems() {
		return orderLineItems.getLineItems();
	}
	
	public Money getOrderTotal() {
		return orderLineItems.orderTotal();
	}
	
	/**
	 * The factory method for creating an order.
	 * 
	 * @param consumerId
	 * @param restaurant
	 * @param deliveryInformation
	 * @param orderLineItems
	 * @return
	 */
	public static ResultWithDomainEvents<Order, OrderDomainEvent> createOrder(long consumerId, Restaurant restaurant,
			DeliveryInformation deliveryInformation, List<OrderLineItem> orderLineItems) {
		Order order = new Order(consumerId, restaurant.getId(), deliveryInformation, orderLineItems);
		List<OrderDomainEvent> events = singletonList(new OrderCreatedEvent(
				new OrderDetails(consumerId, restaurant.getId(), orderLineItems, order.getOrderTotal()),
				deliveryInformation.getDeliveryAddress(), restaurant.getName()));
		return new ResultWithDomainEvents<>(order, events);
	}

	/**
	 * Approve this order.
	 * 
	 * <p>This method will be invoked when the consumer’s credit card has been successfully authorized.
	 * 
	 * @return  The list contains the domain event of the order has been approved (authorized).
	 */
	public List<OrderDomainEvent> noteApproved() {
		switch (state) {
		case APPROVAL_PENDING:
			this.state = APPROVED;
			return singletonList(new OrderAuthorizedEvent());
		default:
			throw new UnsupportedStateTransitionException(state);
		}

	}

	/**
	 * Reject this order.
	 * 
	 * <p>This method will be invoked when one of the services rejects the order or authorization fails.
	 * 
	 * @return  The list contains the domain event of the order has been rejected.
	 */
	public List<OrderDomainEvent> noteRejected() {
		switch (state) {
		case APPROVAL_PENDING:
			this.state = REJECTED;
			return singletonList(new OrderRejectedEvent());
		default:
			throw new UnsupportedStateTransitionException(state);
		}

	}
	
	public List<OrderDomainEvent> cancel() {
		switch (state) {
		case APPROVED:
			this.state = OrderState.CANCEL_PENDING;
			return emptyList();
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}
	
	/**
	 * Revise this order.
	 * 
	 * @param  orderRevision
	 *         The details of revising the order.
	 * 
	 * @return  The list contains the domain event of the order starts to be revised.
	 */
	public ResultWithDomainEvents<LineItemQuantityChange, OrderDomainEvent> revise(OrderRevision orderRevision) {
		switch (state) {

		case APPROVED:
			LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRevision);
			if (change.newOrderTotal.isGreaterThanOrEqual(orderMinimum)) {
				throw new OrderMinimumNotMetException();
			}
			this.state = REVISION_PENDING;
			return new ResultWithDomainEvents<>(change,
					singletonList(new OrderRevisionProposedEvent(orderRevision,
							change.currentOrderTotal, change.newOrderTotal)));

		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<OrderDomainEvent> undoPendingCancel() {
		switch (state) {
		case CANCEL_PENDING:
			this.state = OrderState.APPROVED;
			return emptyList();
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<OrderDomainEvent> noteCancelled() {
		switch (state) {
		case CANCEL_PENDING:
			this.state = OrderState.CANCELLED;
			return singletonList(new OrderCancelledEvent());
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<OrderDomainEvent> noteReversingAuthorization() {
		return null;
	}

	public List<OrderDomainEvent> rejectRevision() {
		switch (state) {
		case REVISION_PENDING:
			this.state = APPROVED;
			return emptyList();
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<OrderDomainEvent> confirmRevision(OrderRevision orderRevision) {
		switch (state) {
		case REVISION_PENDING:
			LineItemQuantityChange licd = orderLineItems.lineItemQuantityChange(orderRevision);

			orderRevision.getDeliveryInformation().ifPresent(newDi -> this.deliveryInformation = newDi);

			if (!orderRevision.getRevisedLineItemQuantities().isEmpty()) {
				orderLineItems.updateLineItems(orderRevision);
			}

			this.state = APPROVED;
			return singletonList(new OrderRevisedEvent(orderRevision, licd.currentOrderTotal, licd.newOrderTotal));
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}
}
