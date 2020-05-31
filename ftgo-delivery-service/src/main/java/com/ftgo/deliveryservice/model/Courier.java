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

import javax.persistence.*;
import java.util.List;

/**
 * The entity class for couriers.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Access(AccessType.FIELD)
public class Courier {
	@Id
	private long id;

	@Embedded
	private Plan plan;

	private Boolean available;

	private Courier() {
	}

	public Courier(long courierId) {
		this.id = courierId;
		this.plan = new Plan();
	}

	public static Courier create(long courierId) {
		return new Courier(courierId);
	}

	public void noteAvailable() {
		this.available = true;

	}

	public void addAction(Action action) {
		plan.add(action);
	}

	public void cancelDelivery(long deliveryId) {
		plan.removeDelivery(deliveryId);
	}

	public boolean isAvailable() {
		return available;
	}

	public Plan getPlan() {
		return plan;
	}

	public long getId() {
		return id;
	}

	public void noteUnavailable() {
		this.available = false;
	}

	public List<Action> actionsForDelivery(long deliveryId) {
		return plan.actionsForDelivery(deliveryId);
	}
}
