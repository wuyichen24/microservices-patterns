package com.ftgo.kitchenservice.api;

/**
 * Define kitchen service's inbound command channel and aggregates' event outbound channel.
 * 
 * @author  Wuyi Chen
 * @date    04/14/2020
 * @version 1.0
 * @since   1.0
 */
public class KitchenServiceChannels {
	public static final String kitchenServiceChannel = "kitchenService";
	public static final String TICKET_EVENT_CHANNEL  = "com.ftgo.kitchenservice.model.Ticket";
}
