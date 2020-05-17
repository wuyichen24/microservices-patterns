package com.ftgo.restaurantservice.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
	private Money  price;
	
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
