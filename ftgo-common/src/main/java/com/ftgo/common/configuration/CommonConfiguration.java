package com.ftgo.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ftgo.common.domain.CommonJsonMapperInitializer;

/**
 * The configuration class to instantiate for the common library.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
public class CommonConfiguration {
	@Bean
	public CommonJsonMapperInitializer commonJsonMapperInitializer() {
		return new CommonJsonMapperInitializer();
	}
}
