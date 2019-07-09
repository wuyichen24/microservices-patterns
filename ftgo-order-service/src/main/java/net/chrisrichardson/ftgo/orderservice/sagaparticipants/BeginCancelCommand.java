package net.chrisrichardson.ftgo.orderservice.sagaparticipants;

public class BeginCancelCommand extends OrderCommand {
	public BeginCancelCommand(long orderId) {
		super(orderId);
	}
}
