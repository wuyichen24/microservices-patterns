package com.ftgo.orderservice.command.model;

/**
 * The command for confirming an order has been cancelled.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class ConfirmCancelOrderCommand extends OrderCommand {
	public ConfirmCancelOrderCommand() {}
	
	public ConfirmCancelOrderCommand(long orderId) {
		super(orderId);
	}
}
