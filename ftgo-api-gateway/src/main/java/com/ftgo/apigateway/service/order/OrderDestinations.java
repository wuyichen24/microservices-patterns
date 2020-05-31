/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
