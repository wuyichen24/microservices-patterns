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
package com.ftgo.orderhistoryservice.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class AvMapBuilder {
	private Map<String, AttributeValue> eav = new HashMap<>();

	public AvMapBuilder(String key, AttributeValue value) {
		eav.put(key, value);
	}

	public AvMapBuilder add(String key, String value) {
		eav.put(key, new AttributeValue(value));
		return this;
	}

	public AvMapBuilder add(String key, AttributeValue value) {
		eav.put(key, value);
		return this;
	}

	public Map<String, AttributeValue> map() {
		return eav;
	}
}
