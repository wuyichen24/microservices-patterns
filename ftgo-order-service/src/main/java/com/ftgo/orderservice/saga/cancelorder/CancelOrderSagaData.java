package com.ftgo.orderservice.saga.cancelorder;

import com.ftgo.common.model.Money;

/**
 * The collected data for the saga of cancelling an order.
 *
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
public class CancelOrderSagaData {
	private Long   orderId;
	private String reverseRequestId;
	private long   restaurantId;
	private long   consumerId;
	private Money  orderTotal;

	public CancelOrderSagaData(long consumerId, long orderId, Money orderTotal) {
		this.consumerId = consumerId;
		this.orderId    = orderId;
		this.orderTotal = orderTotal;
	}

	public Long getOrderId()                                 { return orderId;                           }
	public String getReverseRequestId()                      { return reverseRequestId;                  }
	public void setReverseRequestId(String reverseRequestId) { this.reverseRequestId = reverseRequestId; }
	public long getRestaurantId()                            { return restaurantId;                      }
	public void setRestaurantId(long restaurantId)           { this.restaurantId = restaurantId;         }
	public long getConsumerId()                              { return consumerId;                        }
	public Money getOrderTotal()                             { return orderTotal;                        }
}
