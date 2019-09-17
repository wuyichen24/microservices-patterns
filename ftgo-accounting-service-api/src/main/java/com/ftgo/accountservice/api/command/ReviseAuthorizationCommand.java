package com.ftgo.accountservice.api.command;

import com.ftgo.common.model.Money;

import io.eventuate.tram.commands.common.Command;

public class ReviseAuthorizationCommand implements Command {
	private long  consumerId;
	private Long  orderId;
	private Money orderTotal;

	public ReviseAuthorizationCommand(long consumerId, Long orderId, Money orderTotal) {
		this.consumerId = consumerId;
		this.orderId    = orderId;
		this.orderTotal = orderTotal;
	}

	public long  getConsumerId()                 { return consumerId;            }
	public void  setConsumerId(long consumerId)  { this.consumerId = consumerId; }
	public Money getOrderTotal()                 { return orderTotal;            }
	public void  setOrderTotal(Money orderTotal) { this.orderTotal = orderTotal; }
	public Long  getOrderId()                    { return orderId;               }
	public void  setOrderId(Long orderId)        { this.orderId = orderId;       }
}
