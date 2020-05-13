package com.ftgo.orderservice.saga.reviseorder;

import com.ftgo.common.model.Money;
import com.ftgo.orderservice.controller.model.OrderRevision;

/**
 * The collected data for the saga of revising an order.
 *
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
public class ReviseOrderSagaData {
	private OrderRevision orderRevision;
	private Long          orderId;
	private Long          expectedVersion;
	private long          restaurantId;
	private Money         revisedOrderTotal;
	private long          consumerId;

	public ReviseOrderSagaData(long consumerId, Long orderId, Long expectedVersion, OrderRevision orderRevision) {
		this.consumerId      = consumerId;
		this.orderId         = orderId;
		this.expectedVersion = expectedVersion;
		this.orderRevision   = orderRevision;
	}

	public Long          getExpectedVersion()                          { return expectedVersion;                     }
	public void          setExpectedVersion(Long expectedVersion)      { this.expectedVersion = expectedVersion;     }
	public void          setRevisedOrderTotal(Money revisedOrderTotal) { this.revisedOrderTotal = revisedOrderTotal; }
	public void          setConsumerId(long consumerId)                { this.consumerId = consumerId;               }
	public OrderRevision getOrderRevision()                            { return orderRevision;                       }
	public void          setOrderRevision(OrderRevision orderRevision) { this.orderRevision = orderRevision;         }
	public Long          getOrderId()                                  { return orderId;                             }
	public void          setOrderId(Long orderId)                      { this.orderId = orderId;                     }
	public long          getRestaurantId()                             { return restaurantId;                        }
	public void          setRestaurantId(long restaurantId)            { this.restaurantId = restaurantId;           }
	public Money         getRevisedOrderTotal()                        { return revisedOrderTotal;                   }
	public long          getConsumerId()                               { return consumerId;                          }
}
