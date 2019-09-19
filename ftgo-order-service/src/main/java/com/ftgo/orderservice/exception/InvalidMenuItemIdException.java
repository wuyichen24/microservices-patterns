package com.ftgo.orderservice.exception;

public class InvalidMenuItemIdException extends RuntimeException {
	public InvalidMenuItemIdException(String menuItemId) {
		super("Invalid menu item id " + menuItemId);
	}
}
