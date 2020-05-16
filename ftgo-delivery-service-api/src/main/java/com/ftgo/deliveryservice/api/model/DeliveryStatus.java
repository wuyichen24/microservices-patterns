package com.ftgo.deliveryservice.api.model;

import java.util.List;

/**
 * The delivery status.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class DeliveryStatus {
	private DeliveryInfo     deliveryInfo;
	private Long             assignedCourier;
	private List<ActionInfo> courierActions;

	public DeliveryStatus() {
	}
	
	public DeliveryStatus(DeliveryInfo deliveryInfo, Long assignedCourier, List<ActionInfo> courierActions) {
		this.deliveryInfo    = deliveryInfo;
		this.assignedCourier = assignedCourier;
		this.courierActions  = courierActions;
	}

	public DeliveryInfo     getDeliveryInfo()                                  { return deliveryInfo;                    }
	public void             setDeliveryInfo(DeliveryInfo deliveryInfo)         { this.deliveryInfo = deliveryInfo;       }
	public Long             getAssignedCourier()                               { return assignedCourier;                 }
	public void             setAssignedCourier(Long assignedCourier)           { this.assignedCourier = assignedCourier; }
	public List<ActionInfo> getCourierActions()                                { return courierActions;                  }
	public void             setCourierActions(List<ActionInfo> courierActions) { this.courierActions = courierActions;   }
}
