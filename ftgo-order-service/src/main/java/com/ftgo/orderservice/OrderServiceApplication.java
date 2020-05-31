/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import com.ftgo.orderservice.configuration.OrderServiceCommandConfiguration;
import com.ftgo.orderservice.configuration.OrderServiceGrpcConfiguration;
import com.ftgo.orderservice.configuration.OrderServiceEventConfiguration;
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
@Import({ OrderServiceWebConfiguration.class, OrderServiceCommandConfiguration.class, OrderServiceEventConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class, OrderServiceGrpcConfiguration.class, MicroserviceCanvasWebConfiguration.class })
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
