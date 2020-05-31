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
