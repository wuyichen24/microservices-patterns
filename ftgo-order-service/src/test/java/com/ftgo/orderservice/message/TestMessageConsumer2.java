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
package com.ftgo.orderservice.message;

import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.util.test.async.Eventually;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestMessageConsumer2 {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private LinkedBlockingDeque<Message> messages = new LinkedBlockingDeque<>();

	private String subscriberId;
	private String channel;

	@Autowired
	private MessageConsumer messageConsumer;

	public TestMessageConsumer2(String subscriberId, String channel) {
		this.subscriberId = subscriberId;
		this.channel = channel;
	}

	@PostConstruct
	public void subscribe() {
		messageConsumer.subscribe(subscriberId, Collections.singleton(channel), this::handle);
	}

	private void handle(Message message) {
		logger.debug("Got message: {}", message);
		messages.add(message);
	}

	public Message assertMessageReceived() {
		return assertMessageReceived((m) -> true);
	}

	public Message assertMessageReceived(Predicate<Message> predicate) {
		return Eventually.eventuallyReturning(() -> {
			Message m = null;
			try {
				m = messages.pollFirst(1, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			assertNotNull(m);
			System.out.println("Testing message: " + m);
			assertTrue("Failed predicate", predicate.test(m));
			return m;
		});
	}
}
