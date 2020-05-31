/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

	public GetConsumerResponse(Long consumerId, PersonName name) {
		super(consumerId);
		this.name       = name;
	}
}
