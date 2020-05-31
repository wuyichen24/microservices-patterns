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
package com.ftgo.deliveryservice;

import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ftgo.deliveryservice.configuration.DeliveryServiceEventConfiguration;
import com.ftgo.deliveryservice.configuration.DeliveryServiceWebConfiguration;
import com.ftgo.deliveryservice.model.Delivery;
import com.ftgo.deliveryservice.repository.DeliveryRepository;
import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;

/**
 * The bootstrap class for the delivery service.
 * 
 * @author Wuyi Chen
 * @date 05/07/2020
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@EnableAutoConfiguration
@Import({ DeliveryServiceWebConfiguration.class, DeliveryServiceEventConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class})
@EntityScan(basePackageClasses = {Delivery.class})
@EnableJpaRepositories(basePackageClasses = {DeliveryRepository.class})
public class DeliveryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}
}
