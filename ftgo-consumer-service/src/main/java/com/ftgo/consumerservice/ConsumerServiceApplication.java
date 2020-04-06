package com.ftgo.consumerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.ftgo.consumerservice.domain.ConsumerWebConfiguration;
import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;

import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

/**
 * The bootstrap class for the consumer service.
 * 
 * @author  Wuyi Chen
 * @date    09/16/2019
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@Import({ ConsumerWebConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class })
public class ConsumerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsumerServiceApplication.class, args);
	}
}
