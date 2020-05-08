package com.ftgo.deliveryservice;

import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.deliveryservice.domain.DeliveryServiceWebConfiguration;
import com.ftgo.deliveryservice.message.DeliveryServiceMessagingConfiguration;
import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;

/**
 * The bootstrap class for the delivery service.
 * 
 * @author  Wuyi Chen
 * @date    05/07/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableAutoConfiguration
@Import({DeliveryServiceMessagingConfiguration.class, DeliveryServiceWebConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class})
public class DeliveryServiceMain {
	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceMain.class, args);
	}
}
