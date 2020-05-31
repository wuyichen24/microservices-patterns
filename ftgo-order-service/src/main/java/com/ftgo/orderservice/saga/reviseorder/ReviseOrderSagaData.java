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
package com.ftgo.orderservice.saga.reviseorder;

import com.ftgo.common.model.Money;
import com.ftgo.orderservice.controller.model.OrderRevision;

/**
 * The collected data for the saga of revising an order.
 *
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
public class ReviseOrderSagaData {
	private OrderRevision orderRevision;
	private Long          orderId;
	private Long          expectedVersion;
	private long          restaurantId;
	private Money         revisedOrderTotal;
	private long          consumerId;

	public ReviseOrderSagaData() {}     // Keep it for serializing from message
	
	public ReviseOrderSagaData(long consumerId, Long orderId, Long expectedVersion, OrderRevision orderRevision) {
		this.consumerId      = consumerId;
		this.orderId         = orderId;
		this.expectedVersion = expectedVersion;
		this.orderRevision   = orderRevision;
	}

	public Long          getExpectedVersion()                          { return expectedVersion;                     }
	public void          setExpectedVersion(Long expectedVersion)      { this.expectedVersion = expectedVersion;     }
	public void          setRevisedOrderTotal(Money revisedOrderTotal) { this.revisedOrderTotal = revisedOrderTotal; }
	public void          setConsumerId(long consumerId)                { this.consumerId = consumerId;               }
	public OrderRevision getOrderRevision()                            { return orderRevision;                       }
	public void          setOrderRevision(OrderRevision orderRevision) { this.orderRevision = orderRevision;         }
	public Long          getOrderId()                                  { return orderId;                             }
	public void          setOrderId(Long orderId)                      { this.orderId = orderId;                     }
	public long          getRestaurantId()                             { return restaurantId;                        }
	public void          setRestaurantId(long restaurantId)            { this.restaurantId = restaurantId;           }
	public Money         getRevisedOrderTotal()                        { return revisedOrderTotal;                   }
	public long          getConsumerId()                               { return consumerId;                          }
}
