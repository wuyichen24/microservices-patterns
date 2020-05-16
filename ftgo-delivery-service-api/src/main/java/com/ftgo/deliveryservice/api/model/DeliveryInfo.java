package com.ftgo.deliveryservice.api.model;

/**
 * The delivery info.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class DeliveryInfo {
	private long   id;
	private String state;

	public DeliveryInfo() {
	}

	public DeliveryInfo(long id, String state) {
		this.id    = id;
		this.state = state;
	}

	public long   getId()                { return id;          }
	public void   setId(long id)         { this.id = id;       }
	public String getState()             { return state;       }
	public void   setState(String state) { this.state = state; }
}
