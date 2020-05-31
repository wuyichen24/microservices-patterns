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
package com.ftgo.accountservice.api.command;

import com.ftgo.common.model.Money;

import io.eventuate.tram.commands.common.Command;

/**
 * The command for authorizing an account.
 *
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class AuthorizeCommand implements Command {
	private long  consumerId;
	private Long  orderId;
	private Money orderTotal;
	private Money amount;

	public AuthorizeCommand() {}
	
	public AuthorizeCommand(long consumerId, Long orderId, Money orderTotal) {
		this.consumerId = consumerId;
		this.orderId    = orderId;
		this.orderTotal = orderTotal;
	}

	public Money getAmount()                     { return amount;                }
	public void  setAmount(Money amount)         { this.amount = amount;         }
	public long  getConsumerId()                 { return consumerId;            }
	public void  setConsumerId(long consumerId)  { this.consumerId = consumerId; }
	public Money getOrderTotal()                 { return orderTotal;            }
	public void  setOrderTotal(Money orderTotal) { this.orderTotal = orderTotal; }
	public Long  getOrderId()                    { return orderId;               }
	public void  setOrderId(Long orderId)        { this.orderId = orderId;       }
}
