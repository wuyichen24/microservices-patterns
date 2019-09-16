package com.ftgo.consumerservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.consumerservice.model.Consumer;

public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
}
