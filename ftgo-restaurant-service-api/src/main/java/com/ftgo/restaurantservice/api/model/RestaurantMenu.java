package com.ftgo.restaurantservice.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.List;

/**
 * The entity class of a restaurant menu.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@Embeddable
@Access(AccessType.FIELD)
public class RestaurantMenu {
	@ElementCollection
	private List<MenuItem> menuItems;

	public RestaurantMenu () {}
	
	public RestaurantMenu(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public List<MenuItem> getMenuItems()                         { return menuItems;           }
	public void           setMenuItems(List<MenuItem> menuItems) { this.menuItems = menuItems; }
	
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
