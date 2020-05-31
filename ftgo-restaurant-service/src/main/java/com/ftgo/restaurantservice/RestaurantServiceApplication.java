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
package com.ftgo.restaurantservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import com.ftgo.restaurantservice.model.Restaurant;
import com.ftgo.restaurantservice.repository.RestaurantRepository;

import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The bootstrap class for the restaurant service.
 * 
 * @author  Wuyi Chen
 * @date    05/07/2020
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@EnableAutoConfiguration
@Import({ TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class })
@ComponentScan
@EntityScan(basePackageClasses = {Restaurant.class})
@EnableJpaRepositories(basePackageClasses = {RestaurantRepository.class})
public class RestaurantServiceApplication {
	@Bean
	public ChannelMapping channelMapping() {
		return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return JSonMapper.objectMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestaurantServiceApplication.class, args);
	}
}
