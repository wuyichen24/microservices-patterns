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
