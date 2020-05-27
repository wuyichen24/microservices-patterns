package com.ftgo.kitchenservice.api.command;


import io.eventuate.tram.commands.common.Command;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ftgo.kitchenservice.api.model.TicketDetails;

/**
 * The command for creating a ticket.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateTicketCommand implements Command {
	private Long          orderId;
	private TicketDetails ticketDetails;
	private long          restaurantId;

	public CreateTicketCommand() {}
	
	public CreateTicketCommand(long restaurantId, long orderId, TicketDetails ticketDetails) {
		this.restaurantId = restaurantId;
		this.orderId = orderId;
		this.ticketDetails = ticketDetails;
	}
	
	public Long          getOrderId()                                 { return orderId;                    }
	public void          setOrderId(Long orderId)                     { this.orderId = orderId;            }
	public TicketDetails getTicketDetails()                           { return ticketDetails;              }
	public void          setTicketDetails(TicketDetails orderDetails) { this.ticketDetails = orderDetails; }
	public long          getRestaurantId()                            { return restaurantId;               }
	public void          setRestaurantId(long restaurantId)           { this.restaurantId = restaurantId;  }
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
