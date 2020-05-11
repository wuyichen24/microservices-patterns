package com.ftgo.apigateway.service.accounting;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "accounting.destinations")
public class AccountingDestinations {
	@NotNull
	private String accountingServiceUrl;

	public String getAccountingServiceUrl()                            { return accountingServiceUrl;                      }
	public void   setAccountingServiceUrl(String accountingServiceUrl) { this.accountingServiceUrl = accountingServiceUrl; }
}
