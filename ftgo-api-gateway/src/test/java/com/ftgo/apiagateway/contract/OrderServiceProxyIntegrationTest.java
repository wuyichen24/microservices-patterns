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

import com.ftgo.apigateway.exception.OrderNotFoundException;
import com.ftgo.apigateway.model.OrderInfo;
import com.ftgo.apigateway.service.order.OrderDestinations;
import com.ftgo.apigateway.service.order.OrderServiceProxy;

import static org.junit.Assert.assertEquals;

/**
 * The integration test for testing OrderServiceProxy class (HTTP client).
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceProxyIntegrationTestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = { "com.ftgo:ftgo-order-service-contracts" })       // It tells Spring Cloud Contract to run the WireMock server on a random port and configure it using the specified contracts.
@DirtiesContext
public class OrderServiceProxyIntegrationTest {
	@Value("${stubrunner.runningstubs.ftgo-order-service-contracts.port}")
	private int port;
	private OrderDestinations orderDestinations;
	private OrderServiceProxy orderService;

	@Before
	public void setUp() throws Exception {
		orderDestinations = new OrderDestinations();
		String orderServiceUrl = "http://localhost:" + port;
		System.out.println("orderServiceUrl=" + orderServiceUrl);
		orderDestinations.setOrderServiceUrl(orderServiceUrl);
		orderService = new OrderServiceProxy(orderDestinations, WebClient.create());
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
