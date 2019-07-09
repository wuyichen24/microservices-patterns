package net.chrisrichardson.ftgo.orderservice.sagaparticipants;

public class ConfirmCancelOrderCommand extends OrderCommand {
	public ConfirmCancelOrderCommand(long orderId) {
		super(orderId);
	}
}
