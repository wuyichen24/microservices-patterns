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

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderHistoryDynamoDBConfiguration {
	@Value("${aws.dynamodb.endpoint.url:#{null}}")
	private String awsDynamodbEndpointUrl;

	@Value("${aws.region}")
	private String awsRegion;

	@Value("${aws.access.key_id:null}")
	private String accessKey;

	@Value("${aws.secret.access.key:null}")
	private String secretKey;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		if (!StringUtils.isBlank(awsDynamodbEndpointUrl)) {
			return AmazonDynamoDBClientBuilder.standard()
					.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamodbEndpointUrl, awsRegion))
					.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
					.build();
		} else {
			return AmazonDynamoDBClientBuilder.standard().withRegion(awsRegion)
					.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
					.build();
		}
	}

	@Bean
	public DynamoDB dynamoDB(AmazonDynamoDB client) {
		return new DynamoDB(client);
	}

	@Bean
	public OrderHistoryDao orderHistoryDao(AmazonDynamoDB client, DynamoDB dynamoDB) {
		return new OrderHistoryDaoDynamoDb(dynamoDB);
	}

	@Bean
	public HealthIndicator dynamoDBHealthIndicator(DynamoDB dynamoDB) {
		return new DynamoDBHealthIndicator(dynamoDB);
	}
}
