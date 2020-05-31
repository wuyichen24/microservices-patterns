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
package com.ftgo.orderservice.api.controller.model;

/**
 * The response for canceling an order API.
 *
 * @author  Wuyi Chen
 * @date    05/13/2020
 * @version 1.0
 * @since   1.0
 */
public class CancelOrderResponse {
	private String message;

	public CancelOrderResponse() {}
	
	public String getMessage() {
		return message;
	}

	public CancelOrderResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public static CancelOrderResponse newBuilder() {
		return new CancelOrderResponse();
	}

	public CancelOrderResponse build() {
		return this;
	}
}
