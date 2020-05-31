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
package com.ftgo.orderservice.controller.model;

import com.ftgo.common.model.Money;

/**
 * The response for getting order API.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class GetOrderResponse {
	private long   orderId;
	private String state;
	private Money  orderTotal;

	public GetOrderResponse(long orderId, String state, Money orderTotal) {
		this.orderId    = orderId;
		this.state      = state;
		this.orderTotal = orderTotal;
	}

	public Money  getOrderTotal()                 { return orderTotal;            }
	public void   setOrderTotal(Money orderTotal) { this.orderTotal = orderTotal; }
	public long   getOrderId()                    { return orderId;               }
	public void   setOrderId(long orderId)        { this.orderId = orderId;       }
	public String getState()                      { return state;                 }
	public void   setState(String state)          { this.state = state;           }
}
