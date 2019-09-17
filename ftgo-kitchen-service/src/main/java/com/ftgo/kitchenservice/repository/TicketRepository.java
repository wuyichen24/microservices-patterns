package com.ftgo.kitchenservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.kitchenservice.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
