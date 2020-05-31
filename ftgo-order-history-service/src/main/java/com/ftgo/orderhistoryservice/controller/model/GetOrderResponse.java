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
package com.ftgo.orderhistoryservice.controller.model;

import com.ftgo.orderservice.api.model.OrderState;

/**
 * The response for getting order API.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
public class GetOrderResponse {
	private String     orderId;
	private OrderState status;
	private long       restaurantId;
	private String     restaurantName;
	
	public GetOrderResponse(String orderId, OrderState status, long restaurantId, String restaurantName) {
		this.orderId        = orderId;
		this.status         = status;
		this.restaurantId   = restaurantId;
		this.restaurantName = restaurantName;
	}

	public OrderState getStatus()                              { return status;                        }
	public void       setStatus(OrderState status)             { this.status = status;                 }
	public long       getRestaurantId()                        { return restaurantId;                  }
	public void       setRestaurantId(long restaurantId)       { this.restaurantId = restaurantId;     }
	public String     getOrderId()                             { return orderId;                       }
	public void       setOrderId(String orderId)               { this.orderId = orderId;               }
	public String     getRestaurantName()                      { return restaurantName;                }
	public void       setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
}
