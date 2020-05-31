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
package com.ftgo.orderservice.event.model;

import com.ftgo.common.model.Money;
import com.ftgo.orderservice.api.event.OrderDomainEvent;
import com.ftgo.orderservice.controller.model.OrderRevision;

/**
 * The event about an order has been revised.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderRevisedEvent implements OrderDomainEvent {
	private final OrderRevision orderRevision;
	private final Money         currentOrderTotal;
	private final Money         newOrderTotal;

	public OrderRevisedEvent(OrderRevision orderRevision, Money currentOrderTotal, Money newOrderTotal) {
		this.orderRevision     = orderRevision;
		this.currentOrderTotal = currentOrderTotal;
		this.newOrderTotal     = newOrderTotal;
	}
	
	public OrderRevision getOrderRevision()     { return orderRevision;     }
	public Money         getCurrentOrderTotal() { return currentOrderTotal; }
	public Money         getNewOrderTotal()     { return newOrderTotal;     }
}
