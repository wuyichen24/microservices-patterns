package net.chrisrichardson.ftgo.kitchenservice.api;

import io.eventuate.tram.commands.CommandDestination;
import io.eventuate.tram.commands.common.Command;
import org.apache.commons.lang.builder.ToStringBuilder;

@CommandDestination("restaurantService")
public class CreateTicket implements Command {
	private Long          orderId;
	private TicketDetails ticketDetails;
	private long          restaurantId;

	public CreateTicket(long restaurantId, long orderId, TicketDetails ticketDetails) {
		this.restaurantId = restaurantId;
		this.orderId = orderId;
		this.ticketDetails = ticketDetails;
	}
	
	public Long          getOrderId()                                 { return orderId;                    }
	public void          setOrderId(Long orderId)                     { this.orderId = orderId;            }
	public TicketDetails getTicketDetails()                           { return ticketDetails;              }
	public void          setTicketDetails(TicketDetails orderDetails) { this.ticketDetails = orderDetails; }
	public long          getRestaurantId()                            { return restaurantId;               }
	public void          setRestaurantId(long restaurantId)           { this.restaurantId = restaurantId;  }
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
