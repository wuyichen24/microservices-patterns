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
package com.ftgo.orderservice.api.controller.model;

import java.util.List;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ftgo.common.model.Address;
import com.ftgo.orderservice.api.json.ParseDeserializer;

/**
 * The request for creating an order API.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateOrderRequest {
	private long           restaurantId;
	private long           consumerId;
	private List<LineItem> lineItems;
	
	@JsonDeserialize(using = ParseDeserializer.class)
	private LocalDateTime  deliveryTime;
	private Address        deliveryAddress;
	
	public CreateOrderRequest(long consumerId, long restaurantId, Address deliveryAddress, LocalDateTime deliveryTime, List<LineItem> lineItems) {
	    this.restaurantId    = restaurantId;
	    this.consumerId      = consumerId;
	    this.deliveryAddress = deliveryAddress;
	    this.deliveryTime    = deliveryTime;
	    this.lineItems       = lineItems;
	}

	private CreateOrderRequest() {}

	public long           getRestaurantId()                           { return restaurantId;                    }
	public void           setRestaurantId(long restaurantId)          { this.restaurantId = restaurantId;       }
	public long           getConsumerId()                             { return consumerId;                      }
	public void           setConsumerId(long consumerId)              { this.consumerId = consumerId;           } 
	public List<LineItem> getLineItems()                              { return lineItems;                       }
	public void           setLineItems(List<LineItem> lineItems)      { this.lineItems = lineItems;             }
	public Address        getDeliveryAddress()                        { return deliveryAddress;                 }
	public void           setDeliveryAddress(Address deliveryAddress) { this.deliveryAddress = deliveryAddress; }
	public LocalDateTime  getDeliveryTime()                           { return deliveryTime;                    }
	public void           setDeliveryTime(LocalDateTime deliveryTime) { this.deliveryTime = deliveryTime;       }

	public static Builder newBuilder() {
		CreateOrderRequest request = new CreateOrderRequest();
		return request.new Builder(request);
	}
	
	public static class LineItem {
		private String menuItemId;
		private int    quantity;

		private LineItem() {}
		public LineItem(String menuItemId, int quantity) {
			this.menuItemId = menuItemId;
			this.quantity   = quantity;
		}

		public String     getMenuItemId()                  { return menuItemId;            }
		public LineItem   setMenuItemId(String menuItemId) { this.menuItemId = menuItemId; return this; }
		public int        getQuantity()                    { return quantity;                           }
		public LineItem   setQuantity(int quantity)        { this.quantity = quantity;    return this; }

		public static LineItem newBuilder() {
			return new LineItem();
		}
		public LineItem build() {
			return this;
		}
	}

	public class Builder {
		private CreateOrderRequest request;
	   
		public Builder(CreateOrderRequest request) {
			this.request = request; 
		}

		public Builder setConsumerId(long consumerId) {
			request.setConsumerId(consumerId);
			return this;
		}

		public Builder setRestaurantId(long restaurantId) {
			request.setRestaurantId(restaurantId);
			return this;
		}

		public Builder setLineItems(int idx, LineItem lineItem) {
			request.getLineItems().set(idx, lineItem);
			return this;
		}
		
		public Builder setDeliveryAddress(Address deliveryAddress) {
			request.setDeliveryAddress(deliveryAddress);
			return this;
		}
		
		public Builder setDeliveryTime(LocalDateTime deliveryTime) {
			request.setDeliveryTime(deliveryTime);
			return this;
		}
		
		public Builder addLineItems(LineItem item) {
			request.lineItems.add(item);
			return this;
		}
		
		public CreateOrderRequest build() {
			return this.request;
		}
	}
}
