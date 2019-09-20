package com.ftgo.orderservice.command.model;


public class BeginCancelCommand extends OrderCommand {
	public BeginCancelCommand(long orderId) {
		super(orderId);
	}
}
