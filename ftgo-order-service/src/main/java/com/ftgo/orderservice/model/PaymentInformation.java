package com.ftgo.orderservice.model;

import javax.persistence.Access;
import javax.persistence.AccessType;

/**
 * The payment information
 * 
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
@Access(AccessType.FIELD)
public class PaymentInformation {
	private String paymentToken;
}
