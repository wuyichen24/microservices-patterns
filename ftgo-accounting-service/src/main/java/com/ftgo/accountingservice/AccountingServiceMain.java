package com.ftgo.accountingservice;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.messaging.AccountingMessagingConfiguration;
import com.ftgo.accountingservice.web.AccountingWebConfiguration;

/**
 * The bootstrap class for the accounting service.
 * 
 * @author  Wuyi Chen
 * @date    09/16/2019
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableAutoConfiguration
@Import({
	AccountingMessagingConfiguration.class, 
	AccountingWebConfiguration.class,
	TramCommandProducerConfiguration.class, 
	EventuateDriverConfiguration.class, 
	TramJdbcKafkaConfiguration.class})
public class AccountingServiceMain {
	@Bean
	public ChannelMapping channelMapping() {
		return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountingServiceMain.class, args);
	}
}
