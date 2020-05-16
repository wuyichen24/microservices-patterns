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
