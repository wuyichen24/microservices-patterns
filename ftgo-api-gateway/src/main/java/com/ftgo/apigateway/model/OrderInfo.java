package com.ftgo.apigateway.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The order information.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderInfo {
	private String orderId;
	private String state;

	/**
	 * @param orderId
	 * @param state
	 */
	public OrderInfo(String orderId, String state) {
		this.orderId = orderId;
		this.state   = state;
	}
	
	public String getState()                 { return state;           }
	public void   setState(String state)     { this.state = state;     }
	public String getOrderId()               { return orderId;         }
	public void   setOrderId(String orderId) { this.orderId = orderId; }

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
