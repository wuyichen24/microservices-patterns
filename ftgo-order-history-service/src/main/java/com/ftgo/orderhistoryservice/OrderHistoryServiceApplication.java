package com.ftgo.orderhistoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import com.ftgo.orderhistoryservice.configuration.OrderHistoryServiceEventConfiguration;
import com.ftgo.orderhistoryservice.configuration.OrderHistoryWebConfiguration;

import io.eventuate.tram.consumer.common.TramConsumerCommonConfiguration;
import io.eventuate.tram.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;

/**
 * The bootstrap class for the order history service.
 * 
 * @author  Wuyi Chen
 * @date    05/01/2020
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@Import({OrderHistoryWebConfiguration.class, OrderHistoryServiceEventConfiguration.class, CommonSwaggerConfiguration.class, TramConsumerCommonConfiguration.class, EventuateTramKafkaMessageConsumerConfiguration.class})
public class OrderHistoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderHistoryServiceApplication.class, args);
	}
}
