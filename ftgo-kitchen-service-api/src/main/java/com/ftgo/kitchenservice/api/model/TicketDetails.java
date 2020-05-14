package com.ftgo.kitchenservice.api.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * The ticket detailed information.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2019
 * @version 1.0
 * @since   1.0
 */
public class TicketDetails {
	private List<TicketLineItem> lineItems;

	public TicketDetails() {}

	public TicketDetails(List<TicketLineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public List<TicketLineItem> getLineItems()                               { return lineItems;           }
	public void                 setLineItems(List<TicketLineItem> lineItems) { this.lineItems = lineItems; }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
