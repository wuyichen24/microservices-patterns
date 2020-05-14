package com.ftgo.kitchenservice.api.controller.model;

import java.time.LocalDateTime;

/**
 * The ticket acceptance information.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2019
 * @version 1.0
 * @since   1.0
 */
public class TicketAcceptance {
	private LocalDateTime readyBy;

	public TicketAcceptance() {
	}

	public TicketAcceptance(LocalDateTime readyBy) {
		this.readyBy = readyBy;
	}

	public LocalDateTime getReadyBy() {
		return readyBy;
	}

	public void setReadyBy(LocalDateTime readyBy) {
		this.readyBy = readyBy;
	}
}
