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

import java.util.List;
import java.util.Optional;

/**
 * The entity class for order history.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderHistory {
	private List<Order>      orders;
	private Optional<String> startKey;

	public OrderHistory(List<Order> orders, Optional<String> startKey) {
		this.orders   = orders;
		this.startKey = startKey;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public Optional<String> getStartKey() {
		return startKey;
	}
}
