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

import com.ftgo.orderservice.api.model.OrderState;

import io.eventuate.tram.events.common.DomainEvent;

/**
 * The event about an order is started to be cancelled.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderCancelRequestedEvent implements DomainEvent {
	private OrderState state;

	public OrderCancelRequestedEvent(OrderState state) {
		this.state = state;
	}

	public OrderState getState() {
		return state;
	}
}
