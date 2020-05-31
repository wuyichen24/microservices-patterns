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

import javax.persistence.ElementCollection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The delivery plan for each courier.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2019
 * @version 1.0
 * @since   1.0
 */
public class Plan {
	@ElementCollection
	private List<Action> actions = new LinkedList<>();

	public void add(Action action) {
		actions.add(action);
	}

	public void removeDelivery(long deliveryId) {
		actions = actions.stream().filter(action -> !action.actionFor(deliveryId)).collect(Collectors.toList());
	}

	public List<Action> getActions() {
		return actions;
	}

	public List<Action> actionsForDelivery(long deliveryId) {
		return actions.stream().filter(action -> action.actionFor(deliveryId)).collect(Collectors.toList());
	}
}
