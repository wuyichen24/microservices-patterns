package com.ftgo.deliveryservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ftgo.deliveryservice.repository.CourierRepository;
import com.ftgo.deliveryservice.repository.DeliveryRepository;
import com.ftgo.deliveryservice.repository.RestaurantRepository;
import com.ftgo.deliveryservice.service.DeliveryService;

/**
 * The configuration class to instantiate and wire the domain service class.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EntityScan
@EnableJpaRepositories
@EnableTransactionManagement
public class DeliveryServiceConfiguration {
	@Bean
	public DeliveryService deliveryService(RestaurantRepository restaurantRepository, DeliveryRepository deliveryRepository, CourierRepository courierRepository) {
		return new DeliveryService(restaurantRepository, deliveryRepository, courierRepository);
	}
}
