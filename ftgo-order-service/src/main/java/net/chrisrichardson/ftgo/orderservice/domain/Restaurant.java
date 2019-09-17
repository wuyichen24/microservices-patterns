package net.chrisrichardson.ftgo.orderservice.domain;

import net.chrisrichardson.ftgo.orderservice.api.events.OrderDomainEvent;
import net.chrisrichardson.ftgo.restaurantservice.events.MenuItem;
import net.chrisrichardson.ftgo.restaurantservice.events.RestaurantMenu;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ftgo.kitchenservice.api.model.TicketDetails;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "order_service_restaurants")
@Access(AccessType.FIELD)
public class Restaurant {
	@Id
	private Long id;

	@Embedded
	@ElementCollection
	@CollectionTable(name = "order_service_restaurant_menu_items")
	private List<MenuItem> menuItems;
	private String name;

	public Restaurant(long id, String name, List<MenuItem> menuItems) {
		this.id        = id;
		this.name      = name;
		this.menuItems = menuItems;
	}
	
	public Long           getId()        { return id;        }
	public String         getName()      { return name;      }
	public List<MenuItem> getMenuItems() { return menuItems; }

	public List<OrderDomainEvent> reviseMenu(RestaurantMenu revisedMenu) {
		throw new UnsupportedOperationException();
	}

	public void verifyRestaurantDetails(TicketDetails ticketDetails) {
		// TODO - implement me
	}

	public Optional<MenuItem> findMenuItem(String menuItemId) {
		return menuItems.stream().filter(mi -> mi.getId().equals(menuItemId)).findFirst();
	}
}
