package com.ftgo.accountingservice.domain;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.service.AccountingServiceConfiguration;

@Configuration
@Import(AccountingServiceConfiguration.class)
@ComponentScan
public class AccountingWebConfiguration {
}
