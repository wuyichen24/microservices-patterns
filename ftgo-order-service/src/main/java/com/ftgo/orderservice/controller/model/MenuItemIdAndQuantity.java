package com.ftgo.orderservice.controller.model;

/**
 * The class represents one item and its quantity.
 * 
 * @author  Wuyi Chen
 * @date    04/11/2020
 * @version 1.0
 * @since   1.0
 */
public class MenuItemIdAndQuantity {
	private String menuItemId;
	private int    quantity;

	public MenuItemIdAndQuantity(String menuItemId, int quantity) {
		this.menuItemId = menuItemId;
		this.quantity   = quantity;
	}
	
	public String getMenuItemId()                  { return menuItemId;            }
	public int    getQuantity()                    { return quantity;              }
	public void   setMenuItemId(String menuItemId) { this.menuItemId = menuItemId; }
}
