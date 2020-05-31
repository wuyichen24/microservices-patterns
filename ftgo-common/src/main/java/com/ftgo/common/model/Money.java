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
package com.ftgo.common.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * The money entity.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class Money {
	public static Money ZERO = new Money(0);
	private BigDecimal amount;

	public Money() {}
	
	public Money(BigDecimal amount) {
		this.amount = amount;
	}

	public Money(String s) {
		this.amount = new BigDecimal(s);
	}

	public Money(int i) {
		this.amount = new BigDecimal(i);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Money money = (Money) o;

		return new EqualsBuilder().append(amount, money.amount).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(amount).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("amount", amount).toString();
	}

	public Money add(Money delta) {
		return new Money(amount.add(delta.amount));
	}

	public boolean isGreaterThanOrEqual(Money other) {
		return amount.compareTo(other.amount) >= 0;
	}

	public String asString() {
		return amount.toPlainString();
	}

	public Money multiply(int x) {
		return new Money(amount.multiply(new BigDecimal(x)));
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
