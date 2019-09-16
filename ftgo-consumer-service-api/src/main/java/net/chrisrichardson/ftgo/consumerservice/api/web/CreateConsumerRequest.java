package net.chrisrichardson.ftgo.consumerservice.api.web;

import com.ftgo.common.model.PersonName;

public class CreateConsumerRequest {
	private PersonName name;

	public CreateConsumerRequest(PersonName name) {
		this.name = name;
	}
	
	public PersonName getName()                { return name;      }
	public void       setName(PersonName name) { this.name = name; }
}
