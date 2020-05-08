package com.ftgo.deliveryservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ftgo.deliveryservice.model.Courier;

import java.util.List;

public interface CourierRepository extends CrudRepository<Courier, Long>, CustomCourierRepository {

	@Query("SELECT c FROM Courier c WHERE c.available = true")
	List<Courier> findAllAvailable();

}
