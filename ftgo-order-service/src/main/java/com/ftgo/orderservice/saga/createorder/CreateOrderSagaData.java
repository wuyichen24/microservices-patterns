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
package com.ftgo.orderservice.saga.createorder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ftgo.orderservice.api.model.OrderDetails;

/**
 * The collected data for the saga of creating an order.
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateOrderSagaData {
	private Long         orderId;
	private long         ticketId;
	private OrderDetails orderDetails;

	public CreateOrderSagaData() {}    // Keep it for serializing from message
	
	public CreateOrderSagaData(Long orderId, OrderDetails orderDetails) {
		this.orderId      = orderId;
		this.orderDetails = orderDetails;
	}
	
	public Long         getOrderId()               { return orderId;           }
	public OrderDetails getOrderDetails()          { return orderDetails;      }
	public void         setOrderId(Long orderId)   { this.orderId = orderId;   }
	public void         setTicketId(long ticketId) { this.ticketId = ticketId; }
	public long         getTicketId()              { return ticketId;          }
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
