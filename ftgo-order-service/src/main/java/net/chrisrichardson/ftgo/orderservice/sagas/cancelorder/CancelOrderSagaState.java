package net.chrisrichardson.ftgo.orderservice.sagas.cancelorder;

public enum CancelOrderSagaState {
	STATE, 
	WAITING_TO_AUTHORIZE, 
	COMPLETED, 
	REVERSING
}
