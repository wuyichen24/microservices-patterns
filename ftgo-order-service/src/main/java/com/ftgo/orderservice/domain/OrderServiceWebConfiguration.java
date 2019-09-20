package com.ftgo.orderservice.domain;

import brave.sampler.Sampler;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.eventuate.javaclient.commonimpl.JSonMapper;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
@Import(OrderServiceWithRepositoriesConfiguration.class)
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
