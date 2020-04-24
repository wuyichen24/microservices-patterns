package com.ftgo.kitchenservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ftgo.kitchenservice.model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
