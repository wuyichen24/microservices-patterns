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
package com.ftgo.accountingservice;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.configuration.AccountingServiceEventConfiguration;
import com.ftgo.accountingservice.configuration.AccountingWebConfiguration;

/**
 * The bootstrap class for the accounting service.
 * 
 * @author  Wuyi Chen
 * @date    05/07/2020
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@Import({AccountingServiceEventConfiguration.class, AccountingWebConfiguration.class, TramCommandProducerConfiguration.class, EventuateDriverConfiguration.class, TramJdbcKafkaConfiguration.class})
public class AccountingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountingServiceApplication.class, args);
	}
}
