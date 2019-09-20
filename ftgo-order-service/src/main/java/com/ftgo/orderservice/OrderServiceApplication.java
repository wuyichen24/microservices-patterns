package com.ftgo.orderservice;

import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.microservices.canvas.extractor.spring.annotations.ServiceDescription;
import io.microservices.canvas.springmvc.MicroserviceCanvasWebConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.ftgo.orderservice.command.OrderServiceCommandHandlersConfiguration;
import com.ftgo.orderservice.domain.OrderServiceWebConfiguration;
import com.ftgo.orderservice.grpc.GrpcConfiguration;
import com.ftgo.orderservice.message.OrderServiceMessageConfiguration;

@SpringBootApplication
@Import({ OrderServiceWebConfiguration.class, OrderServiceCommandHandlersConfiguration.class,
		OrderServiceMessageConfiguration.class,
		TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class,
		GrpcConfiguration.class, MicroserviceCanvasWebConfiguration.class })
@ServiceDescription(description = "Manages Orders", capabilities = "Order Management")
public class OrderServiceApplication {
	@Bean
	public ChannelMapping channelMapping() {
		return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
