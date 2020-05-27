package com.ftgo.consumerservice.api.command;

import io.eventuate.tram.commands.common.Command;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.ftgo.common.model.Money;

/**
 * The command for validating an order by consumer.
 *
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class ValidateOrderByConsumerCommand implements Command {
	private long  consumerId;
	private long  orderId;
	private Money orderTotal;

	public ValidateOrderByConsumerCommand() {}    // Keep it for serializing from message
	
	public ValidateOrderByConsumerCommand(long consumerId, long orderId, Money orderTotal) {
		this.consumerId = consumerId;
		this.orderId    = orderId;
		this.orderTotal = orderTotal;
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Long  getOrderId()                    { return orderId;               }
	public long  getConsumerId()                 { return consumerId;            }
	public void  setConsumerId(long consumerId)  { this.consumerId = consumerId; }
	public void  setOrderId(long orderId)        { this.orderId = orderId;       }
	public Money getOrderTotal()                 { return orderTotal;            }
	public void  setOrderTotal(Money orderTotal) { this.orderTotal = orderTotal; }
}
