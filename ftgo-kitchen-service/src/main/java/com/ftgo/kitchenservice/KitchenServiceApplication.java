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
import com.ftgo.kitchenservice.message.KitchenServiceMessageHandlersConfiguration;

@SpringBootApplication
@Import({ KitchenServiceWebConfiguration.class, KitchenServiceMessageHandlersConfiguration.class,
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
