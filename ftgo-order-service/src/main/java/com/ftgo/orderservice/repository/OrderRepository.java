package com.ftgo.orderservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.orderservice.model.Order;

/**
 * The repository class for {@code Order} entity. 
 * 
 * @author  Wuyi Chen
 * @date    04/10/2020
 * @version 1.0
 * @since   1.0
 */
public interface OrderRepository extends CrudRepository<Order, Long> {}
