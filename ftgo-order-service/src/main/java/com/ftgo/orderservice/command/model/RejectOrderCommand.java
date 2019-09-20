package com.ftgo.orderservice.command.model;


public class RejectOrderCommand extends OrderCommand {
	public RejectOrderCommand(long orderId) {
		super(orderId);
	}
}
