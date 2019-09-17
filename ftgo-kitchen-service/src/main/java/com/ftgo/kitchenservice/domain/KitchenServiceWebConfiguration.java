package com.ftgo.kitchenservice.domain;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(KitchenDomainConfiguration.class)
@ComponentScan
public class KitchenServiceWebConfiguration {
}
