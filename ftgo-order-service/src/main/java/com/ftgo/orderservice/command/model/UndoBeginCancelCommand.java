package com.ftgo.orderservice.command.model;


public class UndoBeginCancelCommand extends OrderCommand {
	public UndoBeginCancelCommand(long orderId) {
		super(orderId);
	}
}
