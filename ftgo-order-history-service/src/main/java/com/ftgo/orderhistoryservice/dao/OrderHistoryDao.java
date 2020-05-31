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
package com.ftgo.orderhistoryservice.dao;

import java.util.Optional;

import com.ftgo.orderhistoryservice.dao.dynamodb.SourceEvent;
import com.ftgo.orderhistoryservice.domain.OrderHistoryFilter;
import com.ftgo.orderhistoryservice.model.Location;
import com.ftgo.orderhistoryservice.model.Order;
import com.ftgo.orderhistoryservice.model.OrderHistory;

public interface OrderHistoryDao {
	/**
	 * Add an order to the order history table.
	 * 
	 * @param  order
	 *         The Order to add.
	 *         
	 * @param  eventSource
	 *         The event contains the eventId and the type and ID of the aggregate that emitted the event.
	 * 
	 * @return
	 */
	boolean addOrder(Order order, Optional<SourceEvent> eventSource);

	OrderHistory findOrderHistory(String consumerId, OrderHistoryFilter filter);

	public boolean cancelOrder(String orderId, Optional<SourceEvent> eventSource);

	void noteTicketPreparationStarted(String orderId);

	void noteTicketPreparationCompleted(String orderId);

	/**
	 * Update the order which has been picked up.
	 * 
	 * @param orderId
	 * @param eventSource
	 */
	void notePickedUp(String orderId, Optional<SourceEvent> eventSource);

	void updateLocation(String orderId, Location location);

	void noteDelivered(String orderId);

	Optional<Order> findOrder(String orderId);
}
