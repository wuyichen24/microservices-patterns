package com.ftgo.orderservice.command.model;


public class UndoBeginReviseOrderCommand extends OrderCommand {
	protected UndoBeginReviseOrderCommand() {}

	public UndoBeginReviseOrderCommand(long orderId) {
		super(orderId);
	}
}
