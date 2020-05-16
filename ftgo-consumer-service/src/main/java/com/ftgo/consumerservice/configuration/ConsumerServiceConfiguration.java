package com.ftgo.consumerservice.configuration;

import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ftgo.common.configuration.CommonConfiguration;
import com.ftgo.consumerservice.service.ConsumerService;

/**
 * The configuration class to instantiate and wire the domain service class.
 * 
 * @author Wuyi Chen
 * @date 05/15/2020
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import({ SagaParticipantConfiguration.class, TramEventsPublisherConfiguration.class, CommonConfiguration.class })
@EnableTransactionManagement
@ComponentScan
public class ConsumerServiceConfiguration {
	@Bean
	public ConsumerService consumerService() {
		return new ConsumerService();
	}
}
