package net.chrisrichardson.ftgo.consumerservice.domain;

import io.eventuate.tram.events.ResultWithEvents;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ftgo.common.model.Money;
import com.ftgo.common.model.PersonName;

import java.util.Optional;

@Transactional
public class ConsumerService {
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
		return rwe;
	}

	public Optional<Consumer> findById(long consumerId) {
		return consumerRepository.findById(consumerId);
	}
}
