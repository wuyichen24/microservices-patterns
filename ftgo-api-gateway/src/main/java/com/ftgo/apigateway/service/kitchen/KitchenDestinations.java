package com.ftgo.apigateway.service.kitchen;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The configuration properties class to bind the destination URL of the kitchen service 
 * from the application properties file.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@ConfigurationProperties(prefix = "kitchen.destinations")
public class KitchenDestinations {
	@NotNull
	private String kitchenServiceUrl;

	public String getKitchenServiceUrl()                         { return kitchenServiceUrl;                   }
	public void   setKitchenServiceUrl(String kitchenServiceUrl) { this.kitchenServiceUrl = kitchenServiceUrl; }
}
