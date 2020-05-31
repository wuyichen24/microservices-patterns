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
package com.ftgo.orderservice.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.ftgo.common.model.Address;

import java.time.LocalDateTime;

/**
 * The delivery information class.
 * 
 * <p>This class stores the delivery address and the desired delivery time.
 * 
 * @author  Wuyi Chen
 * @date    04/16/2019
 * @version 1.0
 * @since   1.0
 */
@Access(AccessType.FIELD)
public class DeliveryInformation {
	private LocalDateTime deliveryTime;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "state", column = @Column(name = "delivery_state")) })
	private Address deliveryAddress;

	public DeliveryInformation() {
	}
	
	public DeliveryInformation(LocalDateTime deliveryTime,
			Address deliveryAddress) {
		this.deliveryTime = deliveryTime;
		this.deliveryAddress = deliveryAddress;
	}

	public LocalDateTime getDeliveryTime()                           { return deliveryTime;                    }
	public void          setDeliveryTime(LocalDateTime deliveryTime) { this.deliveryTime = deliveryTime;       }
	public Address       getDeliveryAddress()                        { return deliveryAddress;                 }
	public void          setDeliveryAddress(Address deliveryAddress) { this.deliveryAddress = deliveryAddress; }
	
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
}