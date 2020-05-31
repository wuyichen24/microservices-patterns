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
package com.ftgo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

import com.ftgo.apigateway.service.accounting.AccountingConfiguration;
import com.ftgo.apigateway.service.consumer.ConsumerConfiguration;
import com.ftgo.apigateway.service.delivery.DeliveryConfiguration;
import com.ftgo.apigateway.service.kitchen.KitchenConfiguration;
import com.ftgo.apigateway.service.order.OrderConfiguration;

/**
 * The bootstrap class for the API gateway.
 * 
 * @author  Wuyi Chen
 * @date    09/16/2019
 * @version 1.0
 * @since   1.0
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@Import({OrderConfiguration.class, AccountingConfiguration.class, ConsumerConfiguration.class, DeliveryConfiguration.class, KitchenConfiguration.class})
public class ApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
