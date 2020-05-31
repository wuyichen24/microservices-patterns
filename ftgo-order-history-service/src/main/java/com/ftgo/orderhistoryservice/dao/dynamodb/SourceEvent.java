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

import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

import java.util.HashMap;

public class SourceEvent {
	String aggregateType;
	String aggregateId;
	String eventId;

	public SourceEvent(String aggregateType, String aggregateId, String eventId) {
		this.aggregateType = aggregateType;
		this.aggregateId   = aggregateId;
		this.eventId       = eventId;
	}

	public String getAggregateType() {
		return aggregateType;
	}

	public UpdateItemSpec addDuplicateDetection(UpdateItemSpec spec) {
		HashMap<String, String> nameMap = spec.getNameMap() == null ? new HashMap<>() : new HashMap<>(spec.getNameMap());
		nameMap.put("#duplicateDetection", "events." + aggregateType + aggregateId);
		HashMap<String, Object> valueMap = new HashMap<>(spec.getValueMap());
		valueMap.put(":eventId", eventId);
		return spec
				.withUpdateExpression(String.format("%s , #duplicateDetection = :eventId", spec.getUpdateExpression()))
				.withNameMap(nameMap).withValueMap(valueMap)
				.withConditionExpression(Expressions.and(spec.getConditionExpression(), "attribute_not_exists(#duplicateDetection) OR #duplicateDetection < :eventId"));
	}
}
