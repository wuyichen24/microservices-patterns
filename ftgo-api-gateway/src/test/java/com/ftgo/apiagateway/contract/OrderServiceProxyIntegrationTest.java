package com.ftgo.apiagateway.contract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import com.ftgo.apiagateway.exception.OrderNotFoundException;
import com.ftgo.apiagateway.orders.OrderDestinations;
import com.ftgo.apiagateway.orders.OrderInfo;
import com.ftgo.apiagateway.services.OrderService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = { "net.chrisrichardson.ftgo:ftgo-order-service-contracts" })
@DirtiesContext
public class OrderServiceProxyIntegrationTest {
	@Value("${stubrunner.runningstubs.ftgo-order-service-contracts.port}")
	private int port;
	private OrderDestinations orderDestinations;
	private OrderService orderService;

	@Before
	public void setUp() throws Exception {
		orderDestinations = new OrderDestinations();
		String orderServiceUrl = "http://localhost:" + port;
		System.out.println("orderServiceUrl=" + orderServiceUrl);
		orderDestinations.setOrderServiceUrl(orderServiceUrl);
		orderService = new OrderService(orderDestinations, WebClient.create());
	}

	@Test
	public void shouldVerifyExistingCustomer() {
		OrderInfo result = orderService.findOrderById("99").block();
		assertEquals("99", result.getOrderId());
		assertEquals("APPROVAL_PENDING", result.getState());
	}

	@Test(expected = OrderNotFoundException.class)
	public void shouldFailToFindMissingOrder() {
		orderService.findOrderById("555").block();
	}
}
