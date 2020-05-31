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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.ftgo.common.model.Address;
import com.ftgo.common.model.Money;
import com.ftgo.orderservice.api.model.OrderDetails;
import com.ftgo.orderservice.api.model.OrderLineItem;
import com.ftgo.orderservice.api.model.OrderState;
import com.ftgo.orderservice.controller.model.MenuItemIdAndQuantity;
import com.ftgo.orderservice.model.DeliveryInformation;
import com.ftgo.orderservice.model.Order;

import static com.ftgo.orderservice.RestaurantTestData.AJANTA_ID;
import static com.ftgo.orderservice.RestaurantTestData.CHICKEN_VINDALOO;
import static com.ftgo.orderservice.RestaurantTestData.CHICKEN_VINDALOO_PRICE;

/**
 * The static class for hardcoding test parameters about order.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderTestData {
	public static long CONSUMER_ID = 1511300065921L;
	public static long ORDER_ID    = 99L;

	public static Order                             CHICKEN_VINDALOO_ORDER                     = makeAjantaOrder();
	public static final int                         CHICKEN_VINDALOO_QUANTITY                  = 5;
	public static final Money                       CHICKEN_VINDALOO_ORDER_TOTAL               = CHICKEN_VINDALOO_PRICE.multiply(5);
	public static final OrderDetails                CHICKEN_VINDALOO_ORDER_DETAILS             = new OrderDetails(CONSUMER_ID, AJANTA_ID, chickenVindalooLineItems(), CHICKEN_VINDALOO_ORDER_TOTAL);
	public static final MenuItemIdAndQuantity       CHICKEN_VINDALOO_MENU_ITEM_AND_QUANTITY    = new MenuItemIdAndQuantity(RestaurantTestData.CHICKEN_VINDALOO_MENU_ITEM_ID, CHICKEN_VINDALOO_QUANTITY);
	public static final List<MenuItemIdAndQuantity> CHICKEN_VINDALOO_MENU_ITEMS_AND_QUANTITIES = Collections.singletonList(CHICKEN_VINDALOO_MENU_ITEM_AND_QUANTITY);
	public static final OrderState                  CHICKEN_VINDALOO_ORDER_STATE               = OrderState.APPROVAL_PENDING;
	
	public static final Address             DELIVERY_ADDRESS     = new Address("9 Amazing View", null, "Oakland", "CA", "94612");
	public static final LocalDateTime       DELIVERY_TIME        = LocalDateTime.now();
	public static final DeliveryInformation DELIVERY_INFORMATION = new DeliveryInformation(DELIVERY_TIME, DELIVERY_ADDRESS);

	public static List<OrderLineItem> chickenVindalooLineItems() {
		return Collections.singletonList(new OrderLineItem(
				CHICKEN_VINDALOO_MENU_ITEM_AND_QUANTITY.getMenuItemId(),
				CHICKEN_VINDALOO, CHICKEN_VINDALOO_PRICE,
				CHICKEN_VINDALOO_MENU_ITEM_AND_QUANTITY.getQuantity()));
	}
	
	private static Order makeAjantaOrder() {
		Order order = new Order(CONSUMER_ID, AJANTA_ID, new DeliveryInformation(DELIVERY_TIME, DELIVERY_ADDRESS), chickenVindalooLineItems());
		order.setId(ORDER_ID);
		return order;
	}
}
