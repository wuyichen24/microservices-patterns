package com.ftgo.orderservice.api.model;

/**
 * The enum type for the states of tickets.
 * 
 * @author  Wuyi Chen
 * @date    04/16/2019
 * @version 1.0
 * @since   1.0
 */
public enum OrderState {
	APPROVAL_PENDING,
	APPROVED,
	REJECTED,
	CANCEL_PENDING,
	CANCELLED,
	REVISION_PENDING,
}
