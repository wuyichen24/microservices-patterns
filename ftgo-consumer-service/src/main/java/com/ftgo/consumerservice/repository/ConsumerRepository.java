package com.ftgo.consumerservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.ftgo.consumerservice.model.Consumer;

/**
 * The repository class for {@code Consumer} entity. 
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
}
