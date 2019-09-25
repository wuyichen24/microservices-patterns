package com.ftgo.orderservice.event.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ftgo.orderservice.api.event.OrderDomainEvent;

public class OrderAuthorizedEvent implements OrderDomainEvent {
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
