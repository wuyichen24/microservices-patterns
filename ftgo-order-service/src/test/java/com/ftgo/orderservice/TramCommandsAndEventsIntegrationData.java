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
package com.ftgo.orderservice;

public class TramCommandsAndEventsIntegrationData {
	private long now = System.currentTimeMillis();
	private String commandDispatcherId             = "command-dispatcher-"              + now;
	private String eventDispatcherId               = "event-dispatcher-"                + now;
	private String consumerServiceCommandChannel   = "consumerServiceCommandChannel-"   + now;
	private String consumerAggregateDestination    = "consumerAggregateDestination-"    + now;
	private String restaurantServiceCommandChannel = "restaurantServiceCommandChannel-" + now;
	private String restaurantAggregateDestination  = "restaurantAggregateDestination-"  + now;
	private String acccountServiceCommandChannel   = "acccountServiceCommandChannel-"   + now;
	private String acccountAggregateDestination    = "acccountAggregateDestination-"    + now;

	public String getRestaurantServiceCommandChannel() { return restaurantServiceCommandChannel; }
	public String getConsumerAggregateDestination()    { return consumerAggregateDestination;    }
	public String getConsumerServiceCommandChannel()   { return consumerServiceCommandChannel;   }
	public String getCommandDispatcherId()             { return commandDispatcherId;             }
	public String getEventDispatcherId()               { return eventDispatcherId;               }
	public String getRestaurantAggregateDestination()  { return restaurantAggregateDestination;  }
	public String getAcccountServiceCommandChannel()   { return acccountServiceCommandChannel;   }
	public String getAcccountAggregateDestination()    { return acccountAggregateDestination;    }
}
