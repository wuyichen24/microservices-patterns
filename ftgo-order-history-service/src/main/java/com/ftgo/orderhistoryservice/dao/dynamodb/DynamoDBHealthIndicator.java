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

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class DynamoDBHealthIndicator implements HealthIndicator {
	private final Table table;
	private DynamoDB dynamoDB;

	public DynamoDBHealthIndicator(DynamoDB dynamoDB) {
		this.dynamoDB = dynamoDB;
		this.table = this.dynamoDB.getTable(OrderHistoryDaoDynamoDb.FTGO_ORDER_HISTORY_BY_ID);
	}

	@Override
	public Health health() {
		try {
			table.getItem(OrderHistoryDaoDynamoDb.makePrimaryKey("999"));
			return Health.up().build();
		} catch (Exception e) {
			return Health.down(e).build();
		}
	}
}
