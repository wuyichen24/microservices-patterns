package com.ftgo.orderservice.model;

import com.ftgo.common.model.Money;

/**
 * The class represents the change of the total amount of an order.
 * 
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
public class LineItemQuantityChange {
	final Money currentOrderTotal;
	final Money newOrderTotal;
	final Money delta;

	public LineItemQuantityChange(Money currentOrderTotal, Money newOrderTotal, Money delta) {
		this.currentOrderTotal = currentOrderTotal;
		this.newOrderTotal     = newOrderTotal;
		this.delta             = delta;
	}

	public Money getCurrentOrderTotal() { return currentOrderTotal; }
	public Money getNewOrderTotal()     { return newOrderTotal;     }
	public Money getDelta()             { return delta;             }
}
