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
import com.ftgo.kitchenservice.domain.KitchenServiceWebConfiguration;
import com.ftgo.kitchenservice.message.KitchenServiceMessageConfiguration;

/**
 * The bootstrap class for the kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    09/18/2019
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@Import({ KitchenServiceWebConfiguration.class, KitchenServiceMessageConfiguration.class,
		TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class })
@EntityScan(basePackages = {"com.ftgo.kitchenservice.model"})
@EnableJpaRepositories(basePackages = {"com.ftgo.kitchenservice.repository"})
public class KitchenServiceApplication {
	@Bean
	public ChannelMapping channelMapping() {
		return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(KitchenServiceApplication.class, args);
	}
}
