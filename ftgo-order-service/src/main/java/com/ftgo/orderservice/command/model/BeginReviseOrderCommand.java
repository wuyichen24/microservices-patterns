package com.ftgo.orderservice.command.model;

import com.ftgo.orderservice.controller.model.OrderRevision;

/**
 * The command for starting to revise an order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class BeginReviseOrderCommand extends OrderCommand {
	private OrderRevision revision;
	
	public BeginReviseOrderCommand(long orderId, OrderRevision revision) {
		super(orderId);
		this.revision = revision;
	}

	public OrderRevision getRevision()                       { return revision;          }
	public void          setRevision(OrderRevision revision) { this.revision = revision; }
}
