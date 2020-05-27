package com.ftgo.orderservice.command.model;

/**
 * The command for approving an order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class ApproveOrderCommand extends OrderCommand {
	public ApproveOrderCommand() {}
	
	public ApproveOrderCommand(long orderId) {
		super(orderId);
	}
}
