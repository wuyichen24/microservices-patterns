package com.ftgo.orderservice.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.ftgo.common.model.Money;

import java.util.List;

/**
 * The order details class.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderDetails {
	private List<OrderLineItem> lineItems;
	private Money               orderTotal;
	private long                restaurantId;
	private long                consumerId;

	public OrderDetails() {}
	
	public OrderDetails(long consumerId, long restaurantId, List<OrderLineItem> lineItems, Money orderTotal) {
		this.consumerId   = consumerId;
		this.restaurantId = restaurantId;
		this.lineItems    = lineItems;
		this.orderTotal   = orderTotal;
	}
	
	public Money               getOrderTotal()                             { return orderTotal;                }
	public void                setOrderTotal(Money orderTotal)             { this.orderTotal = orderTotal;     }
	public List<OrderLineItem> getLineItems()                              { return lineItems;                 }
	public long                getRestaurantId()                           { return restaurantId;              }
	public long                getConsumerId()                             { return consumerId;                }
	public void                setLineItems(List<OrderLineItem> lineItems) { this.lineItems = lineItems;       }
	public void                setRestaurantId(long restaurantId)          { this.restaurantId = restaurantId; }
	public void                setConsumerId(long consumerId)              { this.consumerId = consumerId;     }
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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
