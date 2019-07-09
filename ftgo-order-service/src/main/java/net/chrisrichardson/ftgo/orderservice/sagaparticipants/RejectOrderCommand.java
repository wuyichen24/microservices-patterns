package net.chrisrichardson.ftgo.orderservice.sagaparticipants;

public class RejectOrderCommand extends OrderCommand {
	public RejectOrderCommand(long orderId) {
		super(orderId);
	}
}
