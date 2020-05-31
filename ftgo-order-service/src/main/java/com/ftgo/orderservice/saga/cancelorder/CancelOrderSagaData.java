/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ftgo.orderservice.saga.cancelorder;

import com.ftgo.common.model.Money;

/**
 * The collected data for the saga of canceling an order.
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

	public CancelOrderSagaData() {}   // Keep it for serializing from message
	
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
