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
package com.ftgo.consumerservice.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ftgo.common.model.Money;
import com.ftgo.common.model.PersonName;
import com.ftgo.consumerservice.event.model.ConsumerCreatedEvent;

import io.eventuate.tram.events.publisher.ResultWithEvents;

/**
 * The consumer entity.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "consumers")
@Access(AccessType.FIELD)
public class Consumer {
	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private PersonName name;

	public Consumer() {}         // Keep default constructor for Hibernate
	
	public Consumer(PersonName name) {
		this.name = name;
	}

	public void validateOrderByConsumer(Money orderTotal) {
		// TODO: implement some business logic
	}

	public Long getId() {
		return id;
	}

	public PersonName getName() {
		return name;
	}

	public static ResultWithEvents<Consumer> create(PersonName name) {
		return new ResultWithEvents<>(new Consumer(name), new ConsumerCreatedEvent());
	}
}
