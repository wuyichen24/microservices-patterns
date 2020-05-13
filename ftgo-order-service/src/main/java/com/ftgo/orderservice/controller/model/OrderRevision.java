package com.ftgo.orderservice.controller.model;

import java.util.Map;
import java.util.Optional;

import com.ftgo.orderservice.model.DeliveryInformation;

/**
 * The class to collect an order revision info.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderRevision {
	private Optional<DeliveryInformation> deliveryInformation = Optional.empty();
	private Map<String, Integer>          revisedLineItemQuantities;

	public OrderRevision(Optional<DeliveryInformation> deliveryInformation, Map<String, Integer> revisedLineItemQuantities) {
		this.deliveryInformation = deliveryInformation;
		this.revisedLineItemQuantities = revisedLineItemQuantities;
	}

	public void setDeliveryInformation(Optional<DeliveryInformation> deliveryInformation) {
		this.deliveryInformation = deliveryInformation;
	}

	public void setRevisedLineItemQuantities(Map<String, Integer> revisedLineItemQuantities) {
		this.revisedLineItemQuantities = revisedLineItemQuantities;
	}

	public Optional<DeliveryInformation> getDeliveryInformation() {
		return deliveryInformation;
	}

	public Map<String, Integer> getRevisedLineItemQuantities() {
		return revisedLineItemQuantities;
	}
}
