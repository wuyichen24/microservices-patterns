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

import java.util.List;

/**
 * The response for getting all the historical orders.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
public class GetOrdersResponse {
	private List<GetOrderResponse> orders;
	private String                 startKey;

	public GetOrdersResponse(List<GetOrderResponse> orders, String startKey) {
		this.orders   = orders;
		this.startKey = startKey;
	}

	public List<GetOrderResponse> getOrders()                              { return orders;            }
	public void                   setOrders(List<GetOrderResponse> orders) { this.orders = orders;     }
	public String                 getStartKey()                            { return startKey;          }
	public void                   setStartKey(String startKey)             { this.startKey = startKey; }
}
