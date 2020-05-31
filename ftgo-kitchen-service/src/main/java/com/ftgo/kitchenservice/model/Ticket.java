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
package com.ftgo.kitchenservice.model;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;

import javax.persistence.*;

import com.ftgo.common.exception.NotYetImplementedException;
import com.ftgo.common.exception.UnsupportedStateTransitionException;
import com.ftgo.kitchenservice.api.event.model.TicketAcceptedEvent;
import com.ftgo.kitchenservice.api.event.model.TicketCancelledEvent;
import com.ftgo.kitchenservice.api.event.model.TicketCreatedEvent;
import com.ftgo.kitchenservice.api.event.model.TicketDomainEvent;
import com.ftgo.kitchenservice.api.event.model.TicketPickedUpEvent;
import com.ftgo.kitchenservice.api.event.model.TicketPreparationCompletedEvent;
import com.ftgo.kitchenservice.api.event.model.TicketPreparationStartedEvent;
import com.ftgo.kitchenservice.api.event.model.TicketRevisedEvent;
import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.api.model.TicketLineItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * The entity class for tickets.
 * 
 * <p>It represents an order that a kitchen must prepare for pickup by a courier.
 * 
 * @author  Wuyi Chen
 * @date    04/15/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "tickets")
@Access(AccessType.FIELD)
public class Ticket {
	@Id
	private Long id;

	@Enumerated(EnumType.STRING)
	private TicketState state;                             // the current state of the ticket.
	private TicketState previousState;                     // the previous state of the ticket.

	private Long restaurantId;

	@ElementCollection
	@CollectionTable(name = "ticket_line_items")
	private List<TicketLineItem> lineItems;

	private LocalDateTime readyBy;                         // the estimate of when the order will be ready for pickup.
	private LocalDateTime acceptTime;                      // the time when the order was accepted.
	private LocalDateTime preparingTime;                   // the time when the order was started to prepare
	private LocalDateTime readyForPickupTime;              // the time when the order was completed and ready for pickup.
	private LocalDateTime pickedUpTime;                    // the time when the order was picked up.
	
	public Ticket() {}                                     // Keep default constructor for Hibernate
	
	/**
	 * The factory method to creates a Ticket.
	 * 
	 * @param  restaurantId
	 *         The restaurant ID.
	 * 
	 * @param  id
	 *         The ticket ID provided by the order service.
	 *         
	 * @param  details
	 *         The detailed info of the ticket.
	 *         
	 * @return  The event of creating the ticket.
	 */
	public static ResultWithDomainEvents<Ticket, TicketDomainEvent> create(long restaurantId, Long id, TicketDetails details) {
		return new ResultWithDomainEvents<>(new Ticket(restaurantId, id, details));
	}

	/**
	 * Create a new ticket.
	 * 
	 * @param  restaurantId
	 *         The restaurant ID.
	 * 
	 * @param  id
	 *         The ticket ID provided by the order service.
	 *         
	 * @param  details
	 *         The detailed info of the ticket.
	 */
	public Ticket(long restaurantId, Long id, TicketDetails details) {
		this.restaurantId  = restaurantId;
		this.id            = id;
		this.state         = TicketState.CREATE_PENDING;
		this.lineItems     = details.getLineItems();
	}

	/**
	 * Confirm the ticket has been created.
	 * 
	 * @return  The list contains the domain event of creating the ticket.
	 */
	public List<TicketDomainEvent> confirmCreate() {
		switch (state) {
		case CREATE_PENDING:
			state = TicketState.AWAITING_ACCEPTANCE;
			return singletonList(new TicketCreatedEvent(id, new TicketDetails()));
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<TicketDomainEvent> cancelCreate() {
		throw new NotYetImplementedException();
	}

	/**
	 * The kitchen accepts the order.
	 * 
	 * <p>It generates the domain event of accepting a order.
	 * 
	 * @param  readyBy
	 *         The estimate of when the order will be ready for pickup. 
	 *         
	 * @return  The list contains the domain event of accepting the ticket.
	 */
	public List<TicketDomainEvent> accept(LocalDateTime readyBy) {
		switch (state) {
		case AWAITING_ACCEPTANCE:
			this.state = TicketState.ACCEPTED;
			this.acceptTime = LocalDateTime.now();
			if (!acceptTime.isBefore(readyBy)) {
				throw new IllegalArgumentException(String.format("readyBy %s is not after now %s", readyBy, acceptTime));
			}
			this.readyBy = readyBy;
			return singletonList(new TicketAcceptedEvent(readyBy));
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	/**
	 * The kitchen starts to prepare the order.
	 * 
	 * <p>The order can no longer be changed or cancelled.
	 * 
	 * @return  The list contains the domain event of starting preparing the order.
	 */
	public List<TicketDomainEvent> preparing() {
		switch (state) {
		case ACCEPTED:
			this.state = TicketState.PREPARING;
			this.preparingTime = LocalDateTime.now();
			return singletonList(new TicketPreparationStartedEvent());
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	/**
	 * The order can now be picked up.
	 * 
	 * @return  The list contains the domain event of the order preparation is completed.
	 */
	public List<TicketDomainEvent> readyForPickup() {
		switch (state) {
		case PREPARING:
			this.state = TicketState.READY_FOR_PICKUP;
			this.readyForPickupTime = LocalDateTime.now();
			return singletonList(new TicketPreparationCompletedEvent());
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	/**
	 * The order has been picked up.
	 * 
	 * @return
	 */
	public List<TicketDomainEvent> pickedUp() {
		switch (state) {
		case READY_FOR_PICKUP:
			this.state = TicketState.PICKED_UP;
			this.pickedUpTime = LocalDateTime.now();
			return singletonList(new TicketPickedUpEvent());
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public void changeLineItemQuantity() {
		switch (state) {
		case AWAITING_ACCEPTANCE:
			// TODO
			break;
		case PREPARING:
			// TODO - too late
			break;
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<TicketDomainEvent> cancel() {
		switch (state) {
		case AWAITING_ACCEPTANCE:
		case ACCEPTED:
			this.previousState = state;
			this.state = TicketState.CANCEL_PENDING;
			return emptyList();
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public Long getId() {
		return id;
	}

	public List<TicketDomainEvent> confirmCancel() {
		switch (state) {
		case CANCEL_PENDING:
			this.state = TicketState.CANCELLED;
			return singletonList(new TicketCancelledEvent());
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<TicketDomainEvent> undoCancel() {
		switch (state) {
		case CANCEL_PENDING:
			this.state = this.previousState;
			return emptyList();
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<TicketDomainEvent> beginReviseOrder(Map<String, Integer> revisedLineItemQuantities) {
		switch (state) {
		case AWAITING_ACCEPTANCE:
		case ACCEPTED:
			this.previousState = state;
			this.state = TicketState.REVISION_PENDING;
			return emptyList();
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<TicketDomainEvent> undoBeginReviseOrder() {
		switch (state) {
		case REVISION_PENDING:
			this.state = this.previousState;
			return emptyList();
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}

	public List<TicketDomainEvent> confirmReviseTicket(Map<String, Integer> revisedLineItemQuantities) {
		switch (state) {
		case REVISION_PENDING:
			this.state = this.previousState;
			return singletonList(new TicketRevisedEvent());
		default:
			throw new UnsupportedStateTransitionException(state);
		}
	}
}
