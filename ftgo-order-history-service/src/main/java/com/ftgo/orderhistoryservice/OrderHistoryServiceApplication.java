package com.ftgo.orderhistoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import com.ftgo.orderhistoryservice.domain.OrderHistoryWebConfiguration;
import com.ftgo.orderhistoryservice.message.OrderHistoryServiceMessageConfiguration;

import io.eventuate.tram.consumer.common.TramConsumerCommonConfiguration;
import io.eventuate.tram.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;

@SpringBootApplication
@Import({OrderHistoryWebConfiguration.class, OrderHistoryServiceMessageConfiguration.class, CommonSwaggerConfiguration.class, TramConsumerCommonConfiguration.class, EventuateTramKafkaMessageConsumerConfiguration.class})
public class OrderHistoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderHistoryServiceApplication.class, args);
	}
}
