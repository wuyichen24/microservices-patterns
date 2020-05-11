package com.ftgo.apigateway.service.kitchen;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kitchen.destinations")
public class KitchenDestinations {
	@NotNull
	private String kitchenServiceUrl;

	public String getKitchenServiceUrl()                         { return kitchenServiceUrl;                   }
	public void   setKitchenServiceUrl(String kitchenServiceUrl) { this.kitchenServiceUrl = kitchenServiceUrl; }
}
