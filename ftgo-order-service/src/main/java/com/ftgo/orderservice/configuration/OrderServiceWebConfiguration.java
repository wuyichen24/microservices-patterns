package com.ftgo.orderservice.configuration;

import brave.sampler.Sampler;
import io.eventuate.common.json.mapper.JSonMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.*;

/**
 * The configuration class of external APIs.
 * 
 * @author  Wuyi Chen
 * @date    05/07/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@ComponentScan
@Import(OrderServiceRepositoriesConfiguration.class)
public class OrderServiceWebConfiguration {
	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return JSonMapper.objectMapper;
	}

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
