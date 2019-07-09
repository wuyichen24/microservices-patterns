package net.chrisrichardson.ftgo.orderservice.sagaparticipants;

public class ApproveOrderCommand extends OrderCommand {
	public ApproveOrderCommand(long orderId) {
		super(orderId);
	}
}
