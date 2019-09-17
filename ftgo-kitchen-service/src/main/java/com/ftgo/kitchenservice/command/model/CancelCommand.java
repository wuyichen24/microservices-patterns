package com.ftgo.kitchenservice.command.model;

public class CancelCommand {
	private long    orderId;
	private boolean force;

	public long getOrderId() {
		return orderId;
	}

	public boolean isForce() {
		return force;
	}
}
