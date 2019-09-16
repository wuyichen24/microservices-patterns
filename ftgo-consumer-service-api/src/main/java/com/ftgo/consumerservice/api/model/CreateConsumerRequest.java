package com.ftgo.consumerservice.api.model;

import com.ftgo.common.model.PersonName;

public class CreateConsumerRequest {
	private PersonName name;

	public CreateConsumerRequest(PersonName name) {
		this.name = name;
	}
	
	public PersonName getName()                { return name;      }
	public void       setName(PersonName name) { this.name = name; }
}
