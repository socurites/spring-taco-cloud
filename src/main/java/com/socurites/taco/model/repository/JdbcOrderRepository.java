package com.socurites.taco.model.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socurites.taco.model.Order;
import com.socurites.taco.model.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {
	private final SimpleJdbcInsert orderInserter;
	private final SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;
	
	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order")
				.usingGeneratedKeyColumns("id");
		
		this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order_Tacos");
		
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		
		for (Taco taco : order.getTacos()) {
			saveTacoToOrder(taco, orderId);
		}
		
		return order;
	}
	
	private long saveOrderDetails(Order order) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());
		
		return orderInserter.executeAndReturnKey(values)
			.longValue();
	}

	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Long> values = Map.ofEntries(Map.entry("tacoOrder",orderId),
				Map.entry("taco", taco.getId()));
		
		orderTacoInserter.execute(values);
	}
}
