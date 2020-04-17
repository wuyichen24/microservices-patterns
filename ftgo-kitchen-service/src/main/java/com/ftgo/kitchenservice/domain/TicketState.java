package com.ftgo.kitchenservice.domain;

/**
 * The enum type for the states of tickets.
 * 
 * @author  Wuyi Chen
 * @date    04/16/2019
 * @version 1.0
 * @since   1.0
 */
public enum TicketState {
	CREATE_PENDING, 
	AWAITING_ACCEPTANCE, 
	ACCEPTED, 
	PREPARING, 
	READY_FOR_PICKUP, 
	PICKED_UP, 
	CANCEL_PENDING, 
	CANCELLED, 
	REVISION_PENDING
}
