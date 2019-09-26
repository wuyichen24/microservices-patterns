package net.chrisrichardson.ftgo.deliveryservice.domain;

import javax.persistence.*;

import com.ftgo.common.model.Address;

@Entity
@Access(AccessType.FIELD)
public class Restaurant {

  @Id
  private Long id;

  private String restaurantName;
  private Address address;

  private Restaurant() {
  }

  public Restaurant(long restaurantId, String restaurantName, Address address) {
    this.id = restaurantId;
    this.restaurantName = restaurantName;
    this.address = address;
  }

  public static Restaurant create(long restaurantId, String restaurantName, Address address) {
    return new Restaurant(restaurantId, restaurantName, address);
  }

  public Address getAddress() {
    return address;
  }
}

