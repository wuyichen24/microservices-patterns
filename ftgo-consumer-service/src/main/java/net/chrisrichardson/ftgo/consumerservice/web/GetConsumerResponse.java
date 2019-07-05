package net.chrisrichardson.ftgo.consumerservice.web;

import net.chrisrichardson.ftgo.common.PersonName;
import net.chrisrichardson.ftgo.consumerservice.api.web.CreateConsumerResponse;

public class GetConsumerResponse extends CreateConsumerResponse {
	private PersonName name;

	public PersonName getName() {
		return name;
	}

	public GetConsumerResponse(PersonName name) {
		this.name = name;
	}
}
