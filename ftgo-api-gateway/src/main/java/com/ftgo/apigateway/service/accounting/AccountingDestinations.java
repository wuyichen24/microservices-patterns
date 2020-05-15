package com.ftgo.apigateway.service.accounting;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The configuration properties class to bind the destination URL of the accounting service 
 * from the application properties file.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@ConfigurationProperties(prefix = "accounting.destinations")
public class AccountingDestinations {
	@NotNull
	private String accountingServiceUrl;

	public String getAccountingServiceUrl()                            { return accountingServiceUrl;                      }
	public void   setAccountingServiceUrl(String accountingServiceUrl) { this.accountingServiceUrl = accountingServiceUrl; }
}
