package com.ftgo.orderservice.command.model;

/**
 * The command for undoing the starting of canceling an order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class UndoBeginCancelCommand extends OrderCommand {
	public UndoBeginCancelCommand() {}
	
	public UndoBeginCancelCommand(long orderId) {
		super(orderId);
	}
}
