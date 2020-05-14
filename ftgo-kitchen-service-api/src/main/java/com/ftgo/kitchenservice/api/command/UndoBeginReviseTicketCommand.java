package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

/**
 * The command for undoing a revising ticket.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class UndoBeginReviseTicketCommand implements Command {
	private long restaurantId;
	private Long orderId;

	public UndoBeginReviseTicketCommand() {}

	public UndoBeginReviseTicketCommand(long restaurantId, Long orderId) {
		this.restaurantId = restaurantId;
		this.orderId      = orderId;
	}

	public long getRestaurantId()                  { return restaurantId;              }
	public void setRestaurantId(long restaurantId) { this.restaurantId = restaurantId; }
	public Long getOrderId()                       { return orderId;                   }
	public void setOrderId(Long orderId)           { this.orderId = orderId;           }
}
