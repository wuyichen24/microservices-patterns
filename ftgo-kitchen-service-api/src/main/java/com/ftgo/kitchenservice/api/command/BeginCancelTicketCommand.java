package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

/**
 * The command for starting to cancel a ticket.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class BeginCancelTicketCommand implements Command {
	private long restaurantId;
	private long orderId;

	public BeginCancelTicketCommand() {}
	
	public BeginCancelTicketCommand(long restaurantId, long orderId) {
		this.restaurantId = restaurantId;
		this.orderId      = orderId;
	}

	public long getRestaurantId()                  { return restaurantId;              }
	public void setRestaurantId(long restaurantId) { this.restaurantId = restaurantId; }
	public long getOrderId()                       { return orderId;                   }
	public void setOrderId(long orderId)           { this.orderId = orderId;           }
}
