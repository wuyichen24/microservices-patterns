package com.ftgo.accountingservice.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.domain.AccountServiceConfiguration;

@Configuration
@Import(AccountServiceConfiguration.class)
@ComponentScan
public class AccountingWebConfiguration {
}
