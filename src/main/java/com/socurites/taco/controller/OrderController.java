package com.socurites.taco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.socurites.taco.model.Order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	private final RestTemplate restTemplate;

	public OrderController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(Order order,
			HttpSession session,
			SessionStatus sessionStatus) {
		log.info("Order submitted: " + order);

		log.info("sessionId: " + session.getId());

		Order saved = restTemplate.postForObject("http://localhost:8081/orders", order, Order.class);

		sessionStatus.setComplete();
		
		return "redirect:/";
	}
	
}
