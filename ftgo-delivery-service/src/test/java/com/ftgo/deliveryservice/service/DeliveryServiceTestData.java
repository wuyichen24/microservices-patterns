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
package com.ftgo.deliveryservice.service;

import com.ftgo.common.model.Address;

/**
 * The static class for hardcoding the test data for testing the delivery service.
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public class DeliveryServiceTestData {
	static final Address PICKUP_ADDRESS   = new Address("1 Main Street", "Suite 501", "Oakland", "CA", "94612");
	static final Address DELIVERY_ADDRESS = new Address("1 Quiet Street", "Apartment 101", "Oakland", "CA", "94612");
}
