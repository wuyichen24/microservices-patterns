package com.ftgo.apigateway.service.order;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * The order destination class enables the externalized configuration of backend service URLs.
 * 
 * <p>Specify the URL of the Order Service either as the order.destinations.orderServiceUrl property in a properties file 
 * or as an operating system environment variable, ORDER_DESTINATIONS_ORDER_SERVICE_URL.
 * 
 * @author  Wuyi Chen
 * @date    05/05/2020
 * @version 1.0
 * @since   1.0
 */
@ConfigurationProperties(prefix = "order.destinations")
public class OrderDestinations {
	@NotNull
	private String orderServiceUrl;

	@NotNull
	private String orderHistoryServiceUrl;

	public String getOrderHistoryServiceUrl()                              { return orderHistoryServiceUrl;                        }
	public void   setOrderHistoryServiceUrl(String orderHistoryServiceUrl) { this.orderHistoryServiceUrl = orderHistoryServiceUrl; }
	public String getOrderServiceUrl()                                     { return orderServiceUrl;                               }
	public void   setOrderServiceUrl(String orderServiceUrl)               { this.orderServiceUrl = orderServiceUrl;               }
}
