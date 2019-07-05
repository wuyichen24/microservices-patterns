package net.chrisrichardson.ftgo.common;

import javax.persistence.Embeddable;

@Embeddable
public class PersonName {
	private String firstName;
	private String lastName;

	public PersonName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName  = lastName;
	}

	public String getFirstName() { return firstName; }
	public String getLastName()  { return lastName;  }
}