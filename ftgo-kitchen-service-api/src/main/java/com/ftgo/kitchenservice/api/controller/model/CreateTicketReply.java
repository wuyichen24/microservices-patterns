package com.ftgo.kitchenservice.api.controller.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CreateTicketReply {
	private long ticketId;

	public CreateTicketReply(long ticketId) {
		this.ticketId = ticketId;
	}

	public void setTicketId(long ticketId) { this.ticketId = ticketId; }
	public long getTicketId()              { return ticketId;          }
	
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
