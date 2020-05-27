package com.ftgo.accountservice.api.command;

import com.ftgo.common.model.Money;

import io.eventuate.tram.commands.common.Command;

/**
 * The command for authorizing an account.
 *
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class AuthorizeCommand implements Command {
	private long  consumerId;
	private Long  orderId;
	private Money orderTotal;
	private Money amount;

	public AuthorizeCommand() {}
	
	public AuthorizeCommand(long consumerId, Long orderId, Money orderTotal) {
		this.consumerId = consumerId;
		this.orderId    = orderId;
		this.orderTotal = orderTotal;
	}

	public Money getAmount()                     { return amount;                }
	public void  setAmount(Money amount)         { this.amount = amount;         }
	public long  getConsumerId()                 { return consumerId;            }
	public void  setConsumerId(long consumerId)  { this.consumerId = consumerId; }
	public Money getOrderTotal()                 { return orderTotal;            }
	public void  setOrderTotal(Money orderTotal) { this.orderTotal = orderTotal; }
	public Long  getOrderId()                    { return orderId;               }
	public void  setOrderId(Long orderId)        { this.orderId = orderId;       }
}
