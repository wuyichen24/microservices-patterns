package net.chrisrichardson.ftgo.orderservice.sagaparticipants;

import net.chrisrichardson.ftgo.orderservice.domain.OrderRevision;

public class ConfirmReviseOrderCommand extends OrderCommand {
	private OrderRevision revision;

	public ConfirmReviseOrderCommand(long orderId, OrderRevision revision) {
		super(orderId);
		this.revision = revision;
	}

	public OrderRevision getRevision() {
		return revision;
	}
}
