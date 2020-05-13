package com.ftgo.orderservice.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The configuration class of the repository.
 * 
 * @author  Wuyi Chen
 * @date    05/07/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import({ OrderServiceConfiguration.class })
public class OrderServiceWithRepositoriesConfiguration {

}
