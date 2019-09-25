package com.ftgo.restaurantservice.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Access(AccessType.FIELD)
public class RestaurantMenu {
	@ElementCollection
	private List<MenuItem> menuItems;

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
