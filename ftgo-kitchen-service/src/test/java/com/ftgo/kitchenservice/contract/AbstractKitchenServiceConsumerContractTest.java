package com.ftgo.kitchenservice.contract;

import io.eventuate.tram.springcloudcontractsupport.EventuateContractVerifierConfiguration;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.kitchenservice.api.model.TicketDetails;
import com.ftgo.kitchenservice.message.KitchenServiceMessageConfiguration;
import com.ftgo.kitchenservice.model.Ticket;
import com.ftgo.kitchenservice.service.KitchenService;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * The base class for the setup phase of testing the command consumer in the kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AbstractKitchenServiceConsumerContractTest.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class AbstractKitchenServiceConsumerContractTest {
	@Configuration
	@Import({ KitchenServiceMessageConfiguration.class, EventuateContractVerifierConfiguration.class })
	public static class TestConfiguration {
		@Bean
		public KitchenService kitchenService() {
			return mock(KitchenService.class);
		}
	}

	@Autowired
	private KitchenService kitchenService;

	@Before
	public void setup() {
		reset(kitchenService);
		when(kitchenService.createTicket(eq(1L), eq(99L), any(TicketDetails.class)))
				.thenReturn(new Ticket(1L, 99L, new TicketDetails(Collections.emptyList())));
	}
}
