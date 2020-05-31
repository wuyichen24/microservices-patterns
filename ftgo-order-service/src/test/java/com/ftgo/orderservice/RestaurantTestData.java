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

import java.util.Collections;
import java.util.List;

import com.ftgo.common.model.Address;
import com.ftgo.common.model.Money;
import com.ftgo.orderservice.model.Restaurant;
import com.ftgo.restaurantservice.api.model.MenuItem;
import com.ftgo.restaurantservice.api.model.RestaurantMenu;

/**
 * The static class for hardcoding test parameters about restaurant.
 * 
 * @author  Wuyi Chen
 * @date    05/10/2020
 * @version 1.0
 * @since   1.0
 */
public class RestaurantTestData {
    public static final String   CHICKEN_VINDALOO              = "Chicken Vindaloo";
    public static final String   CHICKEN_VINDALOO_MENU_ITEM_ID = "1";
    public static final Money    CHICKEN_VINDALOO_PRICE        = new Money("12.34");
    public static       MenuItem CHICKEN_VINDALOO_MENU_ITEM    = new MenuItem(CHICKEN_VINDALOO_MENU_ITEM_ID, CHICKEN_VINDALOO, CHICKEN_VINDALOO_PRICE);

    public static final String         AJANTA_RESTAURANT_NAME       = "Ajanta";
    public static final long           AJANTA_ID                    = 1L;
    public static final List<MenuItem> AJANTA_RESTAURANT_MENU_ITEMS = Collections.singletonList(new MenuItem(CHICKEN_VINDALOO_MENU_ITEM_ID, CHICKEN_VINDALOO, CHICKEN_VINDALOO_PRICE));
    public static final RestaurantMenu AJANTA_RESTAURANT_MENU       = new RestaurantMenu(AJANTA_RESTAURANT_MENU_ITEMS);
    public static final Restaurant     AJANTA_RESTAURANT            = new Restaurant(AJANTA_ID, AJANTA_RESTAURANT_NAME, AJANTA_RESTAURANT_MENU_ITEMS);
    
    public static final Address RESTAURANT_ADDRESS = new Address("1 Main Street", "Unit 99", "Oakland", "CA", "94611");
}
