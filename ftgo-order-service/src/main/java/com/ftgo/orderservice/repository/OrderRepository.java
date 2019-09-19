package com.ftgo.orderservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.orderservice.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
