package com.ftgo.orderservice.saga.cancelorder;

public enum CancelOrderSagaState {
	STATE, 
	WAITING_TO_AUTHORIZE, 
	COMPLETED, 
	REVERSING
}
