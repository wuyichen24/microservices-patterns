package com.ftgo.orderservice.saga.createorder;

import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftgo.kitchenservice.api.controller.model.CreateTicketReply;
import com.ftgo.orderservice.saga.proxy.*;

/**
 * A singleton class that defines the saga’s state machine for creating an order.
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaState> {
	private SagaDefinition<CreateOrderSagaState> sagaDefinition;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
     * Construct a {@code CreateOrderSaga}.
     * 
     * <p>It defines the steps of the sage for creating a order.
     */
	public CreateOrderSaga(OrderServiceProxy orderService, ConsumerServiceProxy consumerService, KitchenServiceProxy kitchenService, AccountingServiceProxy accountingService) {
		this.sagaDefinition = step()
				.withCompensation(orderService.reject, CreateOrderSagaState::makeRejectOrderCommand)
				.step().invokeParticipant(consumerService.validateOrder, CreateOrderSagaState::makeValidateOrderByConsumerCommand)
				.step().invokeParticipant(kitchenService.create, CreateOrderSagaState::makeCreateTicketCommand)
				.onReply(CreateTicketReply.class, CreateOrderSagaState::handleCreateTicketReply)
				.withCompensation(kitchenService.cancel, CreateOrderSagaState::makeCancelCreateTicketCommand)
				.step().invokeParticipant(accountingService.authorize, CreateOrderSagaState::makeAuthorizeCommand)
				.step().invokeParticipant(kitchenService.confirmCreate, CreateOrderSagaState::makeConfirmCreateTicketCommand)
				.step().invokeParticipant(orderService.approve, CreateOrderSagaState::makeApproveOrderCommand).build();
	}

	@Override
	public SagaDefinition<CreateOrderSagaState> getSagaDefinition() {
		return sagaDefinition;
	}
}
