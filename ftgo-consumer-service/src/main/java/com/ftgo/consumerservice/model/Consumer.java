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
