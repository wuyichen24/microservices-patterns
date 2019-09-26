package net.chrisrichardson.ftgo.deliveryservice.web;

import net.chrisrichardson.ftgo.deliveryservice.domain.DeliveryServiceDomainConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.common.domain.CommonConfiguration;

@Configuration
@ComponentScan
@Import({DeliveryServiceDomainConfiguration.class, CommonConfiguration.class})
public class DeliveryServiceWebConfiguration {
}
