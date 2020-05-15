package com.ftgo.apigateway.service.order;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.ftgo.apigateway.model.BillInfo;
import com.ftgo.apigateway.model.DeliveryInfo;
import com.ftgo.apigateway.model.OrderInfo;
import com.ftgo.apigateway.model.TicketInfo;

import reactor.util.function.Tuple4;

import java.util.Optional;

/**
 * The order details.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderDetails {
	private OrderInfo orderInfo;

	public OrderDetails() {}
	
	public OrderDetails(OrderInfo orderInfo) { 
		this.orderInfo = orderInfo; 
	}

	public OrderDetails(OrderInfo orderInfo, Optional<TicketInfo> ticketInfo, Optional<DeliveryInfo> deliveryInfo, Optional<BillInfo> billInfo) {
		this(orderInfo);
		// TODO 
		System.out.println("FIXME");
	}

	public OrderInfo getOrderInfo()                    { return orderInfo;           }
	public void      setOrderInfo(OrderInfo orderInfo) { this.orderInfo = orderInfo; }

	public static OrderDetails makeOrderDetails(Tuple4<OrderInfo, Optional<TicketInfo>, Optional<DeliveryInfo>, Optional<BillInfo>> info) {
		return new OrderDetails(info.getT1(), info.getT2(), info.getT3(), info.getT4());
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
}
