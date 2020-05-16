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
