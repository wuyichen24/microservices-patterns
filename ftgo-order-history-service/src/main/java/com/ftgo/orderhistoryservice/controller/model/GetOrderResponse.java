package com.ftgo.orderhistoryservice.controller.model;

import com.ftgo.orderservice.api.model.OrderState;

public class GetOrderResponse {
	private String     orderId;
	private OrderState status;
	private long       restaurantId;
	private String     restaurantName;
	
	public GetOrderResponse(String orderId, OrderState status, long restaurantId, String restaurantName) {
		this.orderId        = orderId;
		this.status         = status;
		this.restaurantId   = restaurantId;
		this.restaurantName = restaurantName;
	}

	public OrderState getStatus()                              { return status;                        }
	public void       setStatus(OrderState status)             { this.status = status;                 }
	public long       getRestaurantId()                        { return restaurantId;                  }
	public void       setRestaurantId(long restaurantId)       { this.restaurantId = restaurantId;     }
	public String     getOrderId()                             { return orderId;                       }
	public void       setOrderId(String orderId)               { this.orderId = orderId;               }
	public String     getRestaurantName()                      { return restaurantName;                }
	public void       setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
}
