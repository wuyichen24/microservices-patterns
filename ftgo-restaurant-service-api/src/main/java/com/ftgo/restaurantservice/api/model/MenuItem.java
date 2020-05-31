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
package com.ftgo.restaurantservice.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.ftgo.common.model.Money;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * The entity class represents a single item in the menu.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@Embeddable
@Access(AccessType.FIELD)
public class MenuItem {
	private String id;
	private String name;
	
	@JsonUnwrapped
	private Money  price;
	
	public MenuItem() {}       // Keep default constructor for Hibernate
	
	public MenuItem(String id, String name, Money price) {
		this.id    = id;
		this.name  = name;
		this.price = price;
	}
	
	public String getId()               { return id;          }
	public void   setId(String id)      { this.id = id;       }
	public String getName()             { return name;        }
	public void   setName(String name)  { this.name = name;   }
	public Money  getPrice()            { return price;       }
	public void   setPrice(Money price) { this.price = price; }

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
