package com.ftgo.kitchenservice.api.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * The item in a ticket.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2019
 * @version 1.0
 * @since   1.0
 */
@Embeddable
@Access(AccessType.FIELD)
public class TicketLineItem {
	private int    quantity;
	private String menuItemId;
	private String name;

	public TicketLineItem() {}
	
	public TicketLineItem(String menuItemId, String name, int quantity) {
		this.menuItemId = menuItemId;
		this.name       = name;
		this.quantity   = quantity;
	}
	
	public int    getQuantity()                    { return quantity;              }
	public void   setQuantity(int quantity)        { this.quantity = quantity;     }
	public String getMenuItemId()                  { return menuItemId;            }
	public void   setMenuItemId(String menuItemId) { this.menuItemId = menuItemId; }
	public String getName()                        { return name;                  }
	public void   setName(String name)             { this.name = name;             }
}
