package com.ftgo.kitchenservice;

import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import net.chrisrichardson.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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
public class KitchenServiceApplication {
	@Bean
	public ChannelMapping channelMapping() {
		return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(KitchenServiceApplication.class, args);
	}
}
