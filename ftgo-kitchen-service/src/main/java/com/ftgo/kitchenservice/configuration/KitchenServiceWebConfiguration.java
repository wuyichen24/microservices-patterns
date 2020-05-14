package com.ftgo.kitchenservice.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The configuration class of external APIs.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@Import(KitchenServiceConfiguration.class)
@ComponentScan
public class KitchenServiceWebConfiguration {
}
