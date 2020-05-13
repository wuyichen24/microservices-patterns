package com.ftgo.orderservice.event.model;

import com.ftgo.common.model.Money;
import com.ftgo.orderservice.api.event.OrderDomainEvent;
import com.ftgo.orderservice.controller.model.OrderRevision;

/**
 * The event about an order has been revised.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderRevisedEvent implements OrderDomainEvent {
	private final OrderRevision orderRevision;
	private final Money         currentOrderTotal;
	private final Money         newOrderTotal;

	public OrderRevisedEvent(OrderRevision orderRevision, Money currentOrderTotal, Money newOrderTotal) {
		this.orderRevision     = orderRevision;
		this.currentOrderTotal = currentOrderTotal;
		this.newOrderTotal     = newOrderTotal;
	}
	
	public OrderRevision getOrderRevision()     { return orderRevision;     }
	public Money         getCurrentOrderTotal() { return currentOrderTotal; }
	public Money         getNewOrderTotal()     { return newOrderTotal;     }
}
