package com.ftgo.apigateway.service.consumer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * The configuration properties class to bind the destination URL of the consumer service 
 * from the application properties file.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@ConfigurationProperties(prefix = "consumer.destinations")
public class ConsumerDestinations {
	@NotNull
	private String consumerServiceUrl;

	public String getConsumerServiceUrl()                          { return consumerServiceUrl;                    }
	public void   setConsumerServiceUrl(String consumerServiceUrl) { this.consumerServiceUrl = consumerServiceUrl; }
}
