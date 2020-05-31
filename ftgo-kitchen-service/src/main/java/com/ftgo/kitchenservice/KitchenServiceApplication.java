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
package com.ftgo.kitchenservice;

import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.messaging.common.ChannelMapping;
import io.eventuate.tram.messaging.common.DefaultChannelMapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import com.ftgo.kitchenservice.configuration.KitchenServiceEventConfiguration;
import com.ftgo.kitchenservice.configuration.KitchenServiceWebConfiguration;
import com.ftgo.kitchenservice.model.Ticket;
import com.ftgo.kitchenservice.repository.TicketRepository;

/**
 * The bootstrap class for the kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@Import({ KitchenServiceWebConfiguration.class, KitchenServiceEventConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class })
@EntityScan(basePackageClasses = {Ticket.class})
@EnableJpaRepositories(basePackageClasses = {TicketRepository.class})
public class KitchenServiceApplication {
	@Bean
	public ChannelMapping channelMapping() {
		return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(KitchenServiceApplication.class, args);
	}
}
