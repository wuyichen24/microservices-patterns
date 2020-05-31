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
package com.ftgo.orderhistoryservice.domain;

import org.joda.time.DateTime;

import com.ftgo.orderservice.api.model.OrderState;

import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * Filter for historical orders
 * 
 * @author  Wuyi Chen
 * @date    04/16/2019
 * @version 1.0
 * @since   1.0
 */
public class OrderHistoryFilter {
	private DateTime             since         = DateTime.now().minusDays(30);
	private Optional<OrderState> status        = Optional.empty();
	private Set<String>          keywords      = emptySet();
	private Optional<String>     startKeyToken = Optional.empty();
	private Optional<Integer>    pageSize      = Optional.empty();

	public DateTime getSince() {
		return since;
	}

	public OrderHistoryFilter withStatus(OrderState status) {
		this.status = Optional.of(status);
		return this;
	}

	public Optional<OrderState> getStatus() {
		return status;
	}

	public OrderHistoryFilter withStartKeyToken(Optional<String> startKeyToken) {
		this.startKeyToken = startKeyToken;
		return this;
	}

	public OrderHistoryFilter withKeywords(Set<String> keywords) {
		this.keywords = keywords;
		return this;
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	public Optional<String> getStartKeyToken() {
		return startKeyToken;
	}

	public OrderHistoryFilter withPageSize(int pageSize) {
		this.pageSize = Optional.of(pageSize);
		return this;
	}

	public Optional<Integer> getPageSize() {
		return pageSize;
	}
}
