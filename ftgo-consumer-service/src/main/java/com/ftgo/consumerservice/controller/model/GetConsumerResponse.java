package com.ftgo.consumerservice.controller.model;

import com.ftgo.common.model.PersonName;
import com.ftgo.consumerservice.api.controller.model.CreateConsumerResponse;

/**
 * The response for getting consumer API.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class GetConsumerResponse extends CreateConsumerResponse {
	private PersonName name;

	public PersonName getName() {
		return name;
	}

	public GetConsumerResponse(PersonName name) {
		this.name = name;
	}
}
