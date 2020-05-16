package com.ftgo.deliveryservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ftgo.deliveryservice.model.Courier;

import java.util.List;

/**
 * The repository class for {@code Courier} entity. 
 * 
 * @author  Wuyi Chen
 * @date    05/16/2020
 * @version 1.0
 * @since   1.0
 */
public interface CourierRepository extends CrudRepository<Courier, Long>, CustomCourierRepository {
	@Query("SELECT c FROM Courier c WHERE c.available = true")
	List<Courier> findAllAvailable();
}
