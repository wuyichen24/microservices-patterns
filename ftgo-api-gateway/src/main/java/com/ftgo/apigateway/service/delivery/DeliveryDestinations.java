package com.ftgo.apigateway.service.delivery;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The configuration properties class to bind the destination URL of the delivery service 
 * from the application properties file.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@ConfigurationProperties(prefix = "delivery.destinations")
public class DeliveryDestinations {
	@NotNull
	private String deliveryServiceUrl;

	public String getDeliveryServiceUrl()                          { return deliveryServiceUrl;                    }
	public void   setDeliveryServiceUrl(String deliveryServiceUrl) { this.deliveryServiceUrl = deliveryServiceUrl; }
}
