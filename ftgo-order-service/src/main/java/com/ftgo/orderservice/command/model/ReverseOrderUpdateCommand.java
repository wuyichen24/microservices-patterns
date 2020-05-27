package com.ftgo.orderservice.command.model;

import io.eventuate.tram.commands.common.Command;

/**
 * The command for reversing the change of an order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class ReverseOrderUpdateCommand implements Command {
	public ReverseOrderUpdateCommand() {}
}
