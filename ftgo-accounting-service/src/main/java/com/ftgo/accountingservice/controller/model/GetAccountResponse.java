package com.ftgo.accountingservice.controller.model;

/**
 * The response for getting account API.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class GetAccountResponse {
	private String accountId;

	public GetAccountResponse() {

	}

	public GetAccountResponse(String accountId) {
		this.accountId = accountId;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
