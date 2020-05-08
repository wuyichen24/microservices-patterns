package com.ftgo.accountingservice;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.domain.AccountingWebConfiguration;
import com.ftgo.accountingservice.message.AccountingServiceMessageConfiguration;

/**
 * The bootstrap class for the accounting service.
 * 
 * @author  Wuyi Chen
 * @date    05/07/2020
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@Import({AccountingServiceMessageConfiguration.class, AccountingWebConfiguration.class, TramCommandProducerConfiguration.class, EventuateDriverConfiguration.class, TramJdbcKafkaConfiguration.class})
public class AccountingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountingServiceApplication.class, args);
	}
}
