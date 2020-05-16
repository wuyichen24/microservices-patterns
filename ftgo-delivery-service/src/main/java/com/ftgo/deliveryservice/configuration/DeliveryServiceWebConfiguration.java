package com.ftgo.deliveryservice.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.configuration.CommonConfiguration;

/**
 * The configuration class of external APIs.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@ComponentScan
@Import({ DeliveryServiceConfiguration.class, CommonConfiguration.class })
public class DeliveryServiceWebConfiguration {
}
