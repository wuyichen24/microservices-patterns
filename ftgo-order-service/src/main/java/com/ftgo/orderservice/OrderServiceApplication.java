package com.ftgo.orderservice;

import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;
import io.microservices.canvas.extractor.spring.annotations.ServiceDescription;
import io.microservices.canvas.springmvc.MicroserviceCanvasWebConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import com.ftgo.orderservice.configuration.OrderServiceCommandHandlersConfiguration;
import com.ftgo.orderservice.configuration.OrderServiceGrpcConfiguration;
import com.ftgo.orderservice.configuration.OrderServiceMessageConfiguration;
import com.ftgo.orderservice.configuration.OrderServiceWebConfiguration;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.repository.OrderRepository;

/**
 * The bootstrap class for the order service.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@Import({ OrderServiceWebConfiguration.class, OrderServiceCommandHandlersConfiguration.class, OrderServiceMessageConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class, OrderServiceGrpcConfiguration.class, MicroserviceCanvasWebConfiguration.class })
@ServiceDescription(description = "Manages Orders", capabilities = "Order Management")
@EntityScan(basePackageClasses = {Order.class})
@EnableJpaRepositories(basePackageClasses = {OrderRepository.class})
public class OrderServiceApplication {
	@Bean
	public ChannelMapping channelMapping() {
		return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
