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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The address entity
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class Address {
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;

	private Address() {
	}

	public Address(String street1, String street2, String city, String state, String zip) {
		this.street1 = street1;
		this.street2 = street2;
		this.city    = city;
		this.state   = state;
		this.zip     = zip;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getStreet1()               { return street1;         }
	public void   setStreet1(String street1) { this.street1 = street1; }
	public String getStreet2()               { return street2;         }
	public void   setStreet2(String street2) { this.street2 = street2; }
	public String getCity()                  { return city;            }
	public void   setCity(String city)       { this.city = city;       }
	public String getState()                 { return state;           }
	public void   setState(String state)     { this.state = state;     }
	public String getZip()                   { return zip;             }
	public void   setZip(String zip)         { this.zip = zip;         }
	
	public static Builder newBuilder() {
		Address address = new Address();
		return address.new Builder(address);
	}
	
	public class Builder {
		private Address address;
		
		public Builder(Address address) {
			this.address = address; 
		}
		
		public Builder setStreet1(String street1) {
			address.setStreet1(street1);
			return this;
		}
		
		public Builder setStreet2(String street2) {
			address.setStreet2(street2);
			return this;
		}
		
		public Builder setState(String state) {
			address.setState(state);
			return this;
		}
		
		public Builder setCity(String city) {
			address.setCity(city);
			return this;
		}
		
		public Builder setZip(String zip) {
			address.setZip(zip);
			return this;
		}
		
		public Address build() {
			return this.address;
		}
	}
}
