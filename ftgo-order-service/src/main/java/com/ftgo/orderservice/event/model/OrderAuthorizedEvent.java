package com.ftgo.orderservice.event.model;

import net.chrisrichardson.ftgo.orderservice.api.events.OrderDomainEvent;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
