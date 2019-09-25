package com.ftgo.restaurantservice.lambda;

import io.eventuate.tram.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.restaurantservice.service.RestaurantServiceDomainConfiguration;

@Configuration
@Import({RestaurantServiceDomainConfiguration.class, TramMessageProducerJdbcConfiguration.class})
@EnableAutoConfiguration
public class RestaurantServiceLambdaConfiguration {
}
