package com.ftgo.accountingservice.configuration.model;

/**
 * The configuration parameters class for the channel configuration.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
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
