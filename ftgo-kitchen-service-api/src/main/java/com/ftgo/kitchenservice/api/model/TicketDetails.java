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
package com.ftgo.kitchenservice.api.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * The ticket detailed information.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2019
 * @version 1.0
 * @since   1.0
 */
public class TicketDetails {
	private List<TicketLineItem> lineItems;

	public TicketDetails() {}

	public TicketDetails(List<TicketLineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public List<TicketLineItem> getLineItems()                               { return lineItems;           }
	public void                 setLineItems(List<TicketLineItem> lineItems) { this.lineItems = lineItems; }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
