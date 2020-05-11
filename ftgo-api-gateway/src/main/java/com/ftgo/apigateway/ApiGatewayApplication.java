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
