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
package com.ftgo.common;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.common.model.Money;

import io.eventuate.common.json.mapper.JSonMapper;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * The test class for testing {@code Money} entity JSON serialization.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class MoneySerializationTest {
	@BeforeClass
	public static void initialize() {
		CommonJsonMapperInitializer.registerMoneyModule();
	}

	public static class MoneyContainer {
		private Money price;

		@Override
		public boolean equals(Object o) {
			return EqualsBuilder.reflectionEquals(this, o);
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}

		public Money getPrice() {
			return price;
		}

		public void setPrice(Money price) {
			this.price = price;
		}

		public MoneyContainer() {

		}

		public MoneyContainer(Money price) {

			this.price = price;
		}
	}

	@Test
	public void shouldSer() {
		Money price = new Money("12.34");
		MoneyContainer mc = new MoneyContainer(price);
		assertEquals("{\"price\":\"12.34\"}", JSonMapper.toJson(mc));
	}

	@Test
	public void shouldDe() {
		Money price = new Money("12.34");
		MoneyContainer mc = new MoneyContainer(price);
		assertEquals(mc, JSonMapper.fromJson("{\"price\":\"12.34\"}", MoneyContainer.class));
	}

	@Test
	public void shouldFailToDe() {
		try {
			JSonMapper.fromJson("{\"price\": { \"amount\" : \"12.34\"} }", MoneyContainer.class);
			fail("expected exception");
		} catch (RuntimeException e) {
			assertEquals(JsonMappingException.class, e.getCause().getClass());
		}
	}
}