package com.ftgo.accountingservice.command.model;

import com.ftgo.common.model.Money;

import io.eventuate.tram.commands.common.Command;

/**
 * The internal command for revising authorization.
 *
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class ReviseAuthorizationCommandInternal implements AccountCommand, Command {
	private String consumerId;
	private String orderId;
	private Money  orderTotal;

	public ReviseAuthorizationCommandInternal(String consumerId, String orderId, Money orderTotal) {
		this.consumerId = consumerId;
		this.orderId    = orderId;
		this.orderTotal = orderTotal;
	}
	
	public String getOrderId()                     { return orderId;               }
	public void   setOrderId(String orderId)       { this.orderId = orderId;       }
	public String getConsumerId()                  { return consumerId;            }
	public void   setConsumerId(String consumerId) { this.consumerId = consumerId; }
	public Money  getOrderTotal()                  { return orderTotal;            }
	public void   setOrderTotal(Money orderTotal)  { this.orderTotal = orderTotal; }
}
