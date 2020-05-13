package com.ftgo.orderservice.exception;

/**
 * The exception to indicate that the id for menu item is not valid.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class InvalidMenuItemIdException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidMenuItemIdException(String menuItemId) {
		super("Invalid menu item id " + menuItemId);
	}
}
