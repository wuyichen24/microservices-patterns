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
package com.ftgo.apigateway.service.order;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.ftgo.apigateway.model.BillInfo;
import com.ftgo.apigateway.model.DeliveryInfo;
import com.ftgo.apigateway.model.OrderInfo;
import com.ftgo.apigateway.model.TicketInfo;

import reactor.util.function.Tuple4;

import java.util.Optional;

/**
 * The order details.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderDetails {
	private OrderInfo orderInfo;

	public OrderDetails() {}
	
	public OrderDetails(OrderInfo orderInfo) { 
		this.orderInfo = orderInfo; 
	}

	public OrderDetails(OrderInfo orderInfo, Optional<TicketInfo> ticketInfo, Optional<DeliveryInfo> deliveryInfo, Optional<BillInfo> billInfo) {
		this(orderInfo);
		// TODO 
		System.out.println("FIXME");
	}

	public OrderInfo getOrderInfo()                    { return orderInfo;           }
	public void      setOrderInfo(OrderInfo orderInfo) { this.orderInfo = orderInfo; }

	public static OrderDetails makeOrderDetails(Tuple4<OrderInfo, Optional<TicketInfo>, Optional<DeliveryInfo>, Optional<BillInfo>> info) {
		return new OrderDetails(info.getT1(), info.getT2(), info.getT3(), info.getT4());
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
}
