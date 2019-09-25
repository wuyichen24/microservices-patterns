package net.chrisrichardson.ftgo.orderservice.api.controller.model;

import java.util.List;

public class CreateOrderRequest {
	private long restaurantId;
	private long consumerId;
	private List<LineItem> lineItems;

	public CreateOrderRequest(long consumerId, long restaurantId, List<LineItem> lineItems) {
		this.restaurantId = restaurantId;
		this.consumerId = consumerId;
		this.lineItems = lineItems;
	}

	private CreateOrderRequest() {}

	public long           getRestaurantId()                      { return restaurantId;              }
	public void           setRestaurantId(long restaurantId)     { this.restaurantId = restaurantId; }
	public long           getConsumerId()                        { return consumerId;                }
	public void           setConsumerId(long consumerId)         { this.consumerId = consumerId;     } 
	public List<LineItem> getLineItems()                         { return lineItems;                 }
	public void           setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems;       }

	public static Builder newBuilder() {
		CreateOrderRequest request = new CreateOrderRequest();
		return request.new Builder(request);
	}
	
	public static class LineItem {
		private String menuItemId;
		private int    quantity;

		private LineItem() {}
		public LineItem(String menuItemId, int quantity) {
			this.menuItemId = menuItemId;
			this.quantity   = quantity;
		}

		public String getMenuItemId()                  { return menuItemId;            }
		public LineItem   setMenuItemId(String menuItemId) { this.menuItemId = menuItemId; return this; }
		public int    getQuantity()                    { return quantity;              }
		public LineItem   setQuantity(int quantity)        { this.quantity = quantity;    return this; }

		public static LineItem newBuilder() {
			return new LineItem();
		}
		public LineItem build() {
			return this;
		}
	}

	public class Builder {
		private CreateOrderRequest request;
	   
		public Builder(CreateOrderRequest request) {
			this.request = request; 
		}

		public Builder setConsumerId(long consumerId) {
			request.setConsumerId(consumerId);
			return this;
		}

		public Builder setRestaurantId(long restaurantId) {
			request.setRestaurantId(restaurantId);
			return this;
		}

		public Builder setLineItems(int idx, LineItem lineItem) {
			request.getLineItems().set(idx, lineItem);
			return this;
		}
		
		public CreateOrderRequest build() {
			return this.request;
		}
	}
}
