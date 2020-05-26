package com.ftgo.orderhistoryservice.controller.model;

import java.util.List;

/**
 * The response for getting all the historical orders.
 * 
 * @author  Wuyi Chen
 * @date    05/26/2020
 * @version 1.0
 * @since   1.0
 */
public class GetOrdersResponse {
	private List<GetOrderResponse> orders;
	private String                 startKey;

	public GetOrdersResponse(List<GetOrderResponse> orders, String startKey) {
		this.orders   = orders;
		this.startKey = startKey;
	}

	public List<GetOrderResponse> getOrders()                              { return orders;            }
	public void                   setOrders(List<GetOrderResponse> orders) { this.orders = orders;     }
	public String                 getStartKey()                            { return startKey;          }
	public void                   setStartKey(String startKey)             { this.startKey = startKey; }
}
