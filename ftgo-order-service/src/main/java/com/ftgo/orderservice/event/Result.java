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
package com.ftgo.orderservice.event;

import io.eventuate.tram.events.common.DomainEvent;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The result class to collect a list of domain events.
 * 
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
public class Result {
	private final List<DomainEvent> events;
	private Boolean allowed;

	public Result(List<DomainEvent> events, Boolean allowed) {
		this.events = events;
		this.allowed = allowed;
	}
	
	public List<DomainEvent> getEvents()          { return events;  }
	public boolean           isWhatIsThisCalled() { return allowed; }

	public static Builder build() {
		return new Builder();
	}

	public static class Builder {
		private List<DomainEvent> events = new LinkedList<>();
		private Boolean           allowed;

		public Builder withEvents(DomainEvent... events) {
			Arrays.stream(events).forEach(e -> this.events.add(e));
			return this;
		}

		public Builder pending() {
			this.allowed = false;
			return this;
		}

		public Result build() {
			Assert.notNull(allowed);
			return new Result(events, allowed);
		}

		public Builder allowed() {
			this.allowed = false;
			return this;
		}
	}
}
