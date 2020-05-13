package com.ftgo.orderservice.command.model;

import io.eventuate.tram.commands.common.Command;

/**
 * The abstract command for order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public abstract class OrderCommand implements Command {
	private long orderId;

	protected OrderCommand() {}

	protected OrderCommand(long orderId) {
		this.orderId = orderId;
	}

	public long getOrderId()             { return orderId;         }
	public void setOrderId(long orderId) { this.orderId = orderId; }
}
