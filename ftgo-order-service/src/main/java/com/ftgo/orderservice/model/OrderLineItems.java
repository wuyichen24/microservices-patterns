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
package com.ftgo.orderservice.model;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import com.ftgo.common.model.Money;
import com.ftgo.orderservice.api.model.OrderLineItem;
import com.ftgo.orderservice.controller.model.OrderRevision;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The class of the collection of items in an order.
 * 
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
@Embeddable
public class OrderLineItems {
	@ElementCollection
	@CollectionTable(name = "order_line_items")
	private List<OrderLineItem> lineItems;

	public OrderLineItems() {}
	
	public OrderLineItems(List<OrderLineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public List<OrderLineItem> getLineItems()                              { return lineItems;           }
	public void                setLineItems(List<OrderLineItem> lineItems) { this.lineItems = lineItems; }

	OrderLineItem findOrderLineItem(String lineItemId) {
		return lineItems.stream()
				.filter(li -> li.getMenuItemId().equals(lineItemId))
				.findFirst().get();
	}

	void updateLineItems(OrderRevision orderRevision) {
		getLineItems().stream().forEach(
				li -> {
					Integer revised = orderRevision
							.getRevisedLineItemQuantities().get(
									li.getMenuItemId());
					li.setQuantity(revised);
				});
	}

	LineItemQuantityChange lineItemQuantityChange(OrderRevision orderRevision) {
		Money currentOrderTotal = orderTotal();
		Money delta             = changeToOrderTotal(orderRevision);
		Money newOrderTotal     = currentOrderTotal.add(delta);
		return new LineItemQuantityChange(currentOrderTotal, newOrderTotal, delta);
	}
	
	Money orderTotal() {
		return lineItems.stream().map(OrderLineItem::getTotal).reduce(Money.ZERO, Money::add);
	}
	
	Money changeToOrderTotal(OrderRevision orderRevision) {
		AtomicReference<Money> delta = new AtomicReference<>(Money.ZERO);

		orderRevision.getRevisedLineItemQuantities().forEach(
				(lineItemId, newQuantity) -> {
					OrderLineItem lineItem = findOrderLineItem(lineItemId);
					delta.set(delta.get().add(lineItem.deltaForChangedQuantity(newQuantity)));
				});
		return delta.get();
	}
}