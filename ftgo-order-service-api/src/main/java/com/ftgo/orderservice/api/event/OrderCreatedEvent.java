package com.ftgo.orderservice.api.event;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.ftgo.common.model.Address;
import com.ftgo.orderservice.api.model.OrderDetails;

/**
 * The event about an order has been created.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderCreatedEvent implements OrderDomainEvent {
	private OrderDetails orderDetails;
	private Address      deliveryAddress;
	private String       restaurantName;

	public OrderCreatedEvent() {}
	
	public OrderCreatedEvent(OrderDetails orderDetails, Address deliveryAddress, String restaurantName) {
	    this.orderDetails    = orderDetails;
	    this.deliveryAddress = deliveryAddress;
	    this.restaurantName  = restaurantName;
	}

	public OrderDetails getOrderDetails()                           { return orderDetails;                    }
	public void         setOrderDetails(OrderDetails orderDetails)  { this.orderDetails = orderDetails;       }
	public String       getRestaurantName()                         { return restaurantName;                  }
	public void         setRestaurantName(String restaurantName)    { this.restaurantName = restaurantName;   }
	public Address      getDeliveryAddress()                        { return deliveryAddress;                 }
	public void         setDeliveryAddress(Address deliveryAddress) { this.deliveryAddress = deliveryAddress; }

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
