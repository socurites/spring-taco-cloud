package com.socurites.taco.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.socurites.taco.model.Ingredient;
import com.socurites.taco.model.Order;
import com.socurites.taco.model.Taco;
import com.socurites.taco.model.Ingredient.Type;
import com.socurites.taco.model.repository.IngredientRepository;
import com.socurites.taco.model.repository.TacoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	private final IngredientRepository ingredientRepositry;
	
	private final TacoRepository tacoRepository;
	
	@Autowired
	private DesignTacoController(IngredientRepository ingredientRepositry,
			TacoRepository tacoRepository) {
		this.ingredientRepositry = ingredientRepositry;
		this.tacoRepository = tacoRepository;
	}
	
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepositry.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		for(Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		
		model.addAttribute("taco", new Taco());
		
		return "design";
	}
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@PostMapping
	public String processDeign(Taco taco,
			@ModelAttribute Order order) {
		log.info("Processing design: " + taco);
		
		Taco saved = tacoRepository.save(taco);
		order.addDesign(saved);
		
		return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
}
