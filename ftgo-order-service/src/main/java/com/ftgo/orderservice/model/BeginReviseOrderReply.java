package com.ftgo.orderservice.model;

import com.ftgo.common.model.Money;

/**
 * The reply for starting to revise an order.
 * 
 * @author  Wuyi Chen
 * @date    04/16/2019
 * @version 1.0
 * @since   1.0
 */
public class BeginReviseOrderReply {
	private Money revisedOrderTotal;

	public BeginReviseOrderReply(Money revisedOrderTotal) {
		this.revisedOrderTotal = revisedOrderTotal;
	}

	public BeginReviseOrderReply() {
	}

	public Money getRevisedOrderTotal()                        { return revisedOrderTotal;                   }
	public void  setRevisedOrderTotal(Money revisedOrderTotal) { this.revisedOrderTotal = revisedOrderTotal; }
}
