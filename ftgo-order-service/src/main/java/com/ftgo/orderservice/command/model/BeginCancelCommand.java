package com.ftgo.orderservice.command.model;

/**
 * The command for starting to cancel an order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class BeginCancelCommand extends OrderCommand {
	private long restaurantId;
	
	public BeginCancelCommand() {}
	
	public BeginCancelCommand(long restaurantId, long orderId) {
		super(orderId);
		this.restaurantId = restaurantId;
	}
}
