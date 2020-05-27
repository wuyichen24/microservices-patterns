package com.ftgo.orderservice.command.model;

import com.ftgo.orderservice.controller.model.OrderRevision;

/**
 * The command for confirming an order has been revised.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class ConfirmReviseOrderCommand extends OrderCommand {
	private OrderRevision revision;

	public ConfirmReviseOrderCommand() {}
	
	public ConfirmReviseOrderCommand(long orderId, OrderRevision revision) {
		super(orderId);
		this.revision = revision;
	}

	public OrderRevision getRevision() {
		return revision;
	}
}
