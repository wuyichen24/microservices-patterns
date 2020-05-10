package com.ftgo.orderservice.saga;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ftgo.accountservice.api.AccountingServiceChannels;
import com.ftgo.accountservice.api.command.AuthorizeCommand;
import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.consumerservice.api.ConsumerServiceChannels;
import com.ftgo.consumerservice.api.command.ValidateOrderByConsumerCommand;
import com.ftgo.kitchenservice.api.KitchenServiceChannels;
import com.ftgo.kitchenservice.api.command.CancelCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand;
import com.ftgo.kitchenservice.api.command.CreateTicketCommand;
import com.ftgo.orderservice.api.OrderServiceChannels;
import com.ftgo.orderservice.command.model.ApproveOrderCommand;
import com.ftgo.orderservice.command.model.RejectOrderCommand;
import com.ftgo.orderservice.saga.createorder.CreateOrderSaga;
import com.ftgo.orderservice.saga.createorder.CreateOrderSagaData;

import static com.ftgo.orderservice.OrderDetailsMother.*;
import static com.ftgo.orderservice.RestaurantMother.AJANTA_ID;
import static io.eventuate.tram.sagas.testing.SagaUnitTestSupport.given;

/**
 * Unit test for the create order saga.
 *
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateOrderSagaTest {
	@BeforeClass
	public static void initialize() {
		CommonJsonMapperInitializer.registerMoneyModule();
	}

	private CreateOrderSaga makeCreateOrderSaga() {
		return new CreateOrderSaga();
	}

	@Test
	public void shouldCreateOrder() {
		given().saga(
				makeCreateOrderSaga(),
				new CreateOrderSagaData(ORDER_ID, CHICKEN_VINDALOO_ORDER_DETAILS))
				.expect()
				.command(new ValidateOrderByConsumerCommand(CONSUMER_ID, ORDER_ID, CHICKEN_VINDALOO_ORDER_TOTAL))
				.to(ConsumerServiceChannels.consumerServiceChannel)
				.andGiven()
				.successReply()
				.expect()
				.command(new CreateTicketCommand(AJANTA_ID, ORDER_ID, null /* FIXME */))
				.to(KitchenServiceChannels.kitchenServiceChannel)
				.andGiven()
				.successReply()
				.expect()
				.command(new AuthorizeCommand(CONSUMER_ID, ORDER_ID, CHICKEN_VINDALOO_ORDER_TOTAL))
				.to(AccountingServiceChannels.accountingServiceChannel)
				.andGiven().successReply().expect()
				.command(new ConfirmCreateTicketCommand(ORDER_ID))
				.to(KitchenServiceChannels.kitchenServiceChannel).andGiven()
				.successReply().expect()
				.command(new ApproveOrderCommand(ORDER_ID))
				.to(OrderServiceChannels.orderServiceChannel);
	}

	@Test
	public void shouldRejectOrderDueToConsumerVerificationFailed() {
		given().saga(
				makeCreateOrderSaga(),
				new CreateOrderSagaData(ORDER_ID, CHICKEN_VINDALOO_ORDER_DETAILS))
				.expect()
				.command(new ValidateOrderByConsumerCommand(CONSUMER_ID, ORDER_ID, CHICKEN_VINDALOO_ORDER_TOTAL))
				.to(ConsumerServiceChannels.consumerServiceChannel).andGiven()
				.failureReply().expect()
				.command(new RejectOrderCommand(ORDER_ID))
				.to(OrderServiceChannels.orderServiceChannel);
	}

	@Test
	public void shouldRejectDueToFailedAuthorizxation() {
		given().saga(
				makeCreateOrderSaga(),
				new CreateOrderSagaData(ORDER_ID, CHICKEN_VINDALOO_ORDER_DETAILS))
				.expect()
				.command(new ValidateOrderByConsumerCommand(CONSUMER_ID, ORDER_ID, CHICKEN_VINDALOO_ORDER_TOTAL))
				.to(ConsumerServiceChannels.consumerServiceChannel)
				.andGiven()
				.successReply()
				.expect()
				.command(new CreateTicketCommand(AJANTA_ID, ORDER_ID, null /* FIXME */))
				.to(KitchenServiceChannels.kitchenServiceChannel)
				.andGiven()
				.successReply()
				.expect()
				.command(new AuthorizeCommand(CONSUMER_ID, ORDER_ID, CHICKEN_VINDALOO_ORDER_TOTAL))
				.to(AccountingServiceChannels.accountingServiceChannel)
				.andGiven().failureReply().expect()
				.command(new CancelCreateTicketCommand(ORDER_ID))
				.to(KitchenServiceChannels.kitchenServiceChannel).andGiven()
				.successReply().expect()
				.command(new RejectOrderCommand(ORDER_ID))
				.to(OrderServiceChannels.orderServiceChannel);
	}
}