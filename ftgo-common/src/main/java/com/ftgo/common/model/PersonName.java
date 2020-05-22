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