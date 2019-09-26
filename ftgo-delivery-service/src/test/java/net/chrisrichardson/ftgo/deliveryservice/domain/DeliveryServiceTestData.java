package net.chrisrichardson.ftgo.deliveryservice.domain;

import com.ftgo.common.model.Address;

public class DeliveryServiceTestData {
  static final Address PICKUP_ADDRESS =
          new Address("1 Main Street", "Suite 501", "Oakland", "CA", "94612");
  static final Address DELIVERY_ADDRESS =
          new Address("1 Quiet Street", "Apartment 101", "Oakland", "CA", "94612");
}
