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
package com.ftgo.consumerservice.service;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.ResultWithEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ftgo.common.model.Money;
import com.ftgo.common.model.PersonName;
import com.ftgo.consumerservice.exception.ConsumerNotFoundException;
import com.ftgo.consumerservice.model.Consumer;
import com.ftgo.consumerservice.repository.ConsumerRepository;

import java.util.Optional;

/**
 * The consumer service class for creating and managing consumers.
 *
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Transactional
public class ConsumerService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	public void validateOrderForConsumer(long consumerId, Money orderTotal) {
		Optional<Consumer> consumer = consumerRepository.findById(consumerId);
		consumer.orElseThrow(ConsumerNotFoundException::new).validateOrderByConsumer(orderTotal);
	}

	public ResultWithEvents<Consumer> create(PersonName name) {
		ResultWithEvents<Consumer> rwe = Consumer.create(name);
		consumerRepository.save(rwe.result);
		domainEventPublisher.publish(Consumer.class, rwe.result.getId(), rwe.events);
		logger.debug("Send ConsumerCreatedEvent to Consumer event channel");
		return rwe;
	}

	public Optional<Consumer> findById(long consumerId) {
		return consumerRepository.findById(consumerId);
	}
}
