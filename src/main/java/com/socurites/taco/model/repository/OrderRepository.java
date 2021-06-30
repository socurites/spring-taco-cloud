package com.socurites.taco.model.repository;

import com.socurites.taco.model.Order;

public interface OrderRepository {
	Order save(Order order);
}
