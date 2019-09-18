package com.ftgo.accountingservice.message;

public class AccountingServiceChannelConfiguration {
	private String commandDispatcherId;
	private String commandChannel;

	public AccountingServiceChannelConfiguration(String commandDispatcherId, String commandChannel) {
		this.commandDispatcherId = commandDispatcherId;
		this.commandChannel      = commandChannel;
	}

	public String getCommandDispatcherId() { return commandDispatcherId; }
	public String getCommandChannel()      { return commandChannel;      }
}
