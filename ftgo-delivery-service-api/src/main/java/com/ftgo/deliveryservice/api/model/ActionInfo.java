package com.ftgo.deliveryservice.api.model;

/**
 * The action info.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class ActionInfo {
	private String type;

	public ActionInfo() {
	}

	public ActionInfo(String type) {
		this.type = type;
	}

	public String getType()            { return type;      }
	public void   setType(String type) { this.type = type; }
}
