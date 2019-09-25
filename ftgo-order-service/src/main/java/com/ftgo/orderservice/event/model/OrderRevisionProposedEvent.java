package com.ftgo.orderservice.event.model;

import com.ftgo.common.model.Money;
import com.ftgo.orderservice.api.event.OrderDomainEvent;
import com.ftgo.orderservice.domain.OrderRevision;

public class OrderRevisionProposedEvent implements OrderDomainEvent {
	private final OrderRevision orderRevision;
	private final Money         currentOrderTotal;
	private final Money         newOrderTotal;

	public OrderRevisionProposedEvent(OrderRevision orderRevision, Money currentOrderTotal, Money newOrderTotal) {
		this.orderRevision = orderRevision;
		this.currentOrderTotal = currentOrderTotal;
		this.newOrderTotal = newOrderTotal;
	}

	public OrderRevision getOrderRevision() { return orderRevision;     }
	public Money getCurrentOrderTotal()     { return currentOrderTotal; }
	public Money getNewOrderTotal()         { return newOrderTotal;     }
}
