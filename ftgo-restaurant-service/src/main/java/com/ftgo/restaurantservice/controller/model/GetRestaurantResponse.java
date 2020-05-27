package com.ftgo.restaurantservice.controller.model;

import com.ftgo.restaurantservice.api.model.RestaurantMenu;

/**
 * The response for getting restaurant API.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class GetRestaurantResponse {
	private Long           id;
	private String         name;
	private RestaurantMenu menu;
	
	public GetRestaurantResponse() {}

	public GetRestaurantResponse(Long id, String name, RestaurantMenu menu) {
		this.id      = id;
		this.name    = name;
		this.menu    = menu;
	}
	
	public Long           getId()                      { return id;              }
	public void           setId(Long id)               { this.id = id;           }
	public String         getName()                    { return name;            }
	public void           setName(String name)         { this.name = name;       }
	public RestaurantMenu getMenu()                    { return menu;            }
	public void           setMenu(RestaurantMenu menu) { this.menu = menu;       }
	
}
