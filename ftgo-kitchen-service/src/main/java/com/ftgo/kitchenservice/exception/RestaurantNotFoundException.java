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
package com.ftgo.kitchenservice.exception;

/**
 * The exception to indicate that the restaurant can not be found by ID.
 * 
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class RestaurantNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(long restaurantId) {
		super("Restaurant not found with id " + restaurantId);
	}
}
