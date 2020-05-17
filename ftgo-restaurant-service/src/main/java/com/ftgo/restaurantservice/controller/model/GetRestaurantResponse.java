package com.ftgo.restaurantservice.controller.model;

/**
 * The response for getting restaurant API.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class GetRestaurantResponse {
	private Long   id;
	private String name;
	
	public GetRestaurantResponse() {}

	public GetRestaurantResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long   getId()              { return id;        }
	public void   setId(Long id)       { this.id = id;     }
	public String getName()            { return name;      }
	public void   setName(String name) { this.name = name; }
}
