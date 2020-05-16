package com.ftgo.consumerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ftgo.consumerservice.configuration.ConsumerWebConfiguration;
import com.ftgo.consumerservice.model.Consumer;
import com.ftgo.consumerservice.repository.ConsumerRepository;
import com.ftgo.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;

import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;

/**
 * The bootstrap class for the consumer service.
 * 
 * @author  Wuyi Chen
 * @date    09/16/2019
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@Import({ ConsumerWebConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class })
@EntityScan(basePackageClasses = {Consumer.class})
@EnableJpaRepositories(basePackageClasses = {ConsumerRepository.class})
public class ConsumerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsumerServiceApplication.class, args);
	}
}
