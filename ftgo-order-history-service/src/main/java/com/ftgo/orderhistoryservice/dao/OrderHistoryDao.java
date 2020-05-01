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
