package com.ftgo.consumerservice.api.controller.model;

import com.ftgo.common.model.PersonName;

/**
 * The request for creating consumer API.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class CreateConsumerRequest {
	private PersonName name;

	public CreateConsumerRequest() {}
	
	public CreateConsumerRequest(PersonName name) {
		this.name = name;
	}
	
	public PersonName getName()                { return name;      }
	public void       setName(PersonName name) { this.name = name; }
}
