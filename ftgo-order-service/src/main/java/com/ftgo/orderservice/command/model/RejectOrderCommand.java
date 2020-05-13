package com.ftgo.orderservice.command.model;

/**
 * The command for rejecting an order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class RejectOrderCommand extends OrderCommand {
	public RejectOrderCommand(long orderId) {
		super(orderId);
	}
}
