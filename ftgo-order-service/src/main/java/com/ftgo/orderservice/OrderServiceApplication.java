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
import com.ftgo.orderservice.command.OrderServiceCommandHandlersConfiguration;
import com.ftgo.orderservice.domain.OrderServiceWebConfiguration;
import com.ftgo.orderservice.grpc.GrpcConfiguration;
import com.ftgo.orderservice.message.OrderServiceMessageConfiguration;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.repository.OrderRepository;

@SpringBootApplication
@Import({ OrderServiceWebConfiguration.class, OrderServiceCommandHandlersConfiguration.class, OrderServiceMessageConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class, GrpcConfiguration.class, MicroserviceCanvasWebConfiguration.class })
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
