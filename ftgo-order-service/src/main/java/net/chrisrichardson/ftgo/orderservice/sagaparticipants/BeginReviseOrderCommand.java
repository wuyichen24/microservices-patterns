package net.chrisrichardson.ftgo.orderservice.sagaparticipants;

import com.ftgo.orderservice.domain.OrderRevision;

public class BeginReviseOrderCommand extends OrderCommand {
	private OrderRevision revision;
	
	public BeginReviseOrderCommand(long orderId, OrderRevision revision) {
		super(orderId);
		this.revision = revision;
	}

	public OrderRevision getRevision()                       { return revision;          }
	public void          setRevision(OrderRevision revision) { this.revision = revision; }
}
