package com.ftgo.kitchenservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ftgo.kitchenservice.model.Ticket;

/**
 * The repository class for {@code Ticket} entity. 
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
