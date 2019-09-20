package com.ftgo.orderservice.command.model;


public class ConfirmCancelOrderCommand extends OrderCommand {
	public ConfirmCancelOrderCommand(long orderId) {
		super(orderId);
	}
}
