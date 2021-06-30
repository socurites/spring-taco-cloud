package com.socurites.taco.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.socurites.taco.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{
}
