package com.ftgo.orderservice.saga.createorder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ftgo.orderservice.api.model.OrderDetails;


/**
 * Represents a saga’s persistent state for creating an order.
 * 
 * <p>The primary responsibility of this class is to create the messages that are sent to saga participants.
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateOrderSagaData {
	private Long         orderId;
	private long         ticketId;
	private OrderDetails orderDetails;

	public CreateOrderSagaData(Long orderId, OrderDetails orderDetails) {
		this.orderId      = orderId;
		this.orderDetails = orderDetails;
	}
	
	public Long         getOrderId()               { return orderId;           }
	public OrderDetails getOrderDetails()          { return orderDetails;      }
	public void         setOrderId(Long orderId)   { this.orderId = orderId;   }
	public void         setTicketId(long ticketId) { this.ticketId = ticketId; }
	public long         getTicketId()              { return ticketId;          }
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
