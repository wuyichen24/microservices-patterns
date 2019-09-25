package com.ftgo.consumerservice.controller.model;

import com.ftgo.common.model.PersonName;
import com.ftgo.consumerservice.api.controller.model.CreateConsumerResponse;

public class GetConsumerResponse extends CreateConsumerResponse {
	private PersonName name;

	public PersonName getName() {
		return name;
	}

	public GetConsumerResponse(PersonName name) {
		this.name = name;
	}
}
