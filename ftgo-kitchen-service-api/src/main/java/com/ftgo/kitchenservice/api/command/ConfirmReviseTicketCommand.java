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
package com.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;

import java.util.Map;

/**
 * The command for confirming the ticket has been revised.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class ConfirmReviseTicketCommand implements Command {
	private long                 restaurantId;
	private long                 orderId;
	private Map<String, Integer> revisedLineItemQuantities;

	public ConfirmReviseTicketCommand() {}
	
	public ConfirmReviseTicketCommand(long restaurantId, Long orderId, Map<String, Integer> revisedLineItemQuantities) {
		this.restaurantId = restaurantId;
		this.orderId = orderId;
		this.revisedLineItemQuantities = revisedLineItemQuantities;
	}

	public long                 getOrderId()                                                                 { return orderId;                                             }
	public void                 setOrderId(long orderId)                                                     { this.orderId = orderId;                                     }
	public long                 getRestaurantId()                                                            { return restaurantId;                                        }
	public void                 setRestaurantId(long restaurantId)                                           { this.restaurantId = restaurantId;                           }
	public Map<String, Integer> getRevisedLineItemQuantities()                                               { return revisedLineItemQuantities;                           }
	public void                 setRevisedLineItemQuantities(Map<String, Integer> revisedLineItemQuantities) { this.revisedLineItemQuantities = revisedLineItemQuantities; }
}
