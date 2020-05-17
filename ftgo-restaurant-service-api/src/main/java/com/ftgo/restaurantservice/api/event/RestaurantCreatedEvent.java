package com.ftgo.restaurantservice.api.event;

import com.ftgo.common.model.Address;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

import io.eventuate.tram.events.common.DomainEvent;

/**
 * The event about a restaurant has been created.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class RestaurantCreatedEvent implements DomainEvent {
	private String         name;
	private Address        address;
	private RestaurantMenu menu;

	public RestaurantCreatedEvent(String name, Address address, RestaurantMenu menu) {
	    this.name    = name;
	    this.address = address;
	    this.menu    = menu;
    }
	
	public String         getName()                    { return name;            }
	public RestaurantMenu getMenu()                    { return menu;            }
	public void           setMenu(RestaurantMenu menu) { this.menu = menu;       }
	public void           setName(String name)         { this.name = name;       }
	public Address        getAddress()                 { return address;         }
	public void           setAddress(Address address)  { this.address = address; }
}
