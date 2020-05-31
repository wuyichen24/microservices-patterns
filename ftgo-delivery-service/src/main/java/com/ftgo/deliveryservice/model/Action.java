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
package com.ftgo.deliveryservice.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ftgo.common.model.Address;

import java.time.LocalDateTime;

/**
 * The single delivery action for each courier.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2019
 * @version 1.0
 * @since   1.0
 */
@Embeddable
public class Action {
	@Enumerated(EnumType.STRING)
	private ActionType type;
	
	@Embedded
	private Address       address;
	private LocalDateTime time;

	protected long        deliveryId;

	private Action() {
	}

	public Action(ActionType type, long deliveryId, Address address, LocalDateTime time) {
		this.type       = type;
		this.deliveryId = deliveryId;
		this.address    = address;
		this.time       = time;
	}

	public boolean actionFor(long deliveryId) {
		return this.deliveryId == deliveryId;
	}

	public static Action makePickup(long deliveryId, Address pickupAddress, LocalDateTime pickupTime) {
		return new Action(ActionType.PICKUP, deliveryId, pickupAddress, pickupTime);
	}

	public static Action makeDropoff(long deliveryId, Address deliveryAddress, LocalDateTime deliveryTime) {
		return new Action(ActionType.DROPOFF, deliveryId, deliveryAddress, deliveryTime);
	}

	public ActionType getType() {
		return type;
	}

	public Address getAddress() {
		return address;
	}
}
