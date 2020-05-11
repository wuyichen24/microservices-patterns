package com.ftgo.apigateway.service.delivery;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "delivery.destinations")
public class DeliveryDestinations {
	@NotNull
	private String deliveryServiceUrl;

	public String getDeliveryServiceUrl()                          { return deliveryServiceUrl;                    }
	public void   setDeliveryServiceUrl(String deliveryServiceUrl) { this.deliveryServiceUrl = deliveryServiceUrl; }
}
