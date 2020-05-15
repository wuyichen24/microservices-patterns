package com.ftgo.apiagateway;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration class for the integration test of the API gateway.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApiGatewayIntegrationTestConfiguration {
	// Force it to be Netty to avoid casting exception in NettyWriteResponseFilter
	// Wiremock adds dependency on Jetty

	@Bean
	public NettyReactiveWebServerFactory NettyReactiveWebServerFactory() {
		return new NettyReactiveWebServerFactory();
	}
}
