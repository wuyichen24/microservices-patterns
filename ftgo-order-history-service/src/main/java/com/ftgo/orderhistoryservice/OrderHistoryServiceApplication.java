package com.ftgo.orderhistoryservice;

import io.eventuate.tram.consumer.kafka.TramConsumerKafkaConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.ftgo.orderhistoryservice.domain.OrderHistoryWebConfiguration;
import com.ftgo.orderhistoryservice.message.OrderHistoryServiceMessageConfiguration;

@SpringBootApplication
@Import({ OrderHistoryWebConfiguration.class, OrderHistoryServiceMessageConfiguration.class,
		TramConsumerKafkaConfiguration.class, CommonSwaggerConfiguration.class })
public class OrderHistoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderHistoryServiceApplication.class, args);
	}
}
