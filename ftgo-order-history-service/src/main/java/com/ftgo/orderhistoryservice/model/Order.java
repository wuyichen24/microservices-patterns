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
package com.ftgo.orderhistoryservice.model;

import org.joda.time.DateTime;

import com.ftgo.common.model.Money;
import com.ftgo.orderservice.api.model.OrderLineItem;
import com.ftgo.orderservice.api.model.OrderState;

import java.util.List;

/**
 * The entity class for orders.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
public class Order {
	private String              consumerId;
	private DateTime            creationDate = DateTime.now();
	private OrderState          status;
	private String              orderId;
	private List<OrderLineItem> lineItems;
	private Money               orderTotal;
	private long                restaurantId;
	private String              restaurantName;

	public Order(String orderId, String consumerId, OrderState status, List<OrderLineItem> lineItems, Money orderTotal, long restaurantId, String restaurantName) {
		this.orderId        = orderId;
		this.consumerId     = consumerId;
		this.status         = status;
		this.lineItems      = lineItems;
		this.orderTotal     = orderTotal;
		this.restaurantId   = restaurantId;
		this.restaurantName = restaurantName;
	}

	public String              getRestaurantName()                    { return restaurantName;            }
	public String              getOrderId()                           { return orderId;                   }
	public long                getRestaurantId()                      { return restaurantId;              }
	public List<OrderLineItem> getLineItems()                         { return lineItems;                 }
	public Money               getOrderTotal()                        { return orderTotal;                }
	public void                setCreationDate(DateTime creationDate) { this.creationDate = creationDate; }
	public String              getConsumerId()                        { return consumerId;                }
	public DateTime            getCreationDate()                      { return creationDate;              }
	public OrderState          getStatus()                            { return status;                    }
}
