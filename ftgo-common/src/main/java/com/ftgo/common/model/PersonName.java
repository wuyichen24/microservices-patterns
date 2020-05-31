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
package com.ftgo.common.model;

import javax.persistence.Embeddable;

/**
 * The person name.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
@Embeddable
public class PersonName {
	private String firstName;
	private String lastName;

	public PersonName () {}       // Keep default constructor for Hibernate
	
	public PersonName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName  = lastName;
	}

	public String getFirstName() { return firstName; }
	public String getLastName()  { return lastName;  }
}