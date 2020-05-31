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
package com.ftgo.orderservice.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.ftgo.common.model.Money;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * The class represents a single item in an order.
 * 
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
@Embeddable
public class OrderLineItem {
	private int    quantity;
	private String menuItemId;
	private String name;

	@Embedded
	@AttributeOverrides(@AttributeOverride(name = "amount", column = @Column(name = "price")))
	private Money price;

	public OrderLineItem() {}
	
	public OrderLineItem(String menuItemId, String name, Money price,
			int quantity) {
		this.menuItemId = menuItemId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public Money deltaForChangedQuantity(int newQuantity) {
		return price.multiply(newQuantity - quantity);
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setMenuItemId(String menuItemId) {
		this.menuItemId = menuItemId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getMenuItemId() {
		return menuItemId;
	}

	public String getName() {
		return name;
	}

	public Money getPrice() {
		return price;
	}

	public Money getTotal() {
		return price.multiply(quantity);
	}

}
