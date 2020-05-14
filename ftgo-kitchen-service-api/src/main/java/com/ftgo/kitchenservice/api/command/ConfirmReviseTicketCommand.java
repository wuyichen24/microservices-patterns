package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

import java.util.Map;

/**
 * The command for confirming the ticket has been revised.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class ConfirmReviseTicketCommand implements Command {
	private long                 restaurantId;
	private long                 orderId;
	private Map<String, Integer> revisedLineItemQuantities;

	public ConfirmReviseTicketCommand(long restaurantId, Long orderId, Map<String, Integer> revisedLineItemQuantities) {
		this.restaurantId = restaurantId;
		this.orderId = orderId;
		this.revisedLineItemQuantities = revisedLineItemQuantities;
	}

	public long                 getOrderId()                                                                 { return orderId;                                             }
	public void                 setOrderId(long orderId)                                                     { this.orderId = orderId;                                     }
	public long                 getRestaurantId()                                                            { return restaurantId;                                        }
	public void                 setRestaurantId(long restaurantId)                                           { this.restaurantId = restaurantId;                           }
	public Map<String, Integer> getRevisedLineItemQuantities()                                               { return revisedLineItemQuantities;                           }
	public void                 setRevisedLineItemQuantities(Map<String, Integer> revisedLineItemQuantities) { this.revisedLineItemQuantities = revisedLineItemQuantities; }
}
