package com.ftgo.orderservice.command.model;

/**
 * The command for undoing the starting of revising an order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class UndoBeginReviseOrderCommand extends OrderCommand {
	protected UndoBeginReviseOrderCommand() {}

	public UndoBeginReviseOrderCommand(long orderId) {
		super(orderId);
	}
}
