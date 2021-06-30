package com.socurites.taco.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import com.socurites.taco.model.Ingredient;
import com.socurites.taco.model.repository.IngredientRepository;

@Configuration
public class IngredientByIdConverter implements Converter<String, Ingredient> {
	private IngredientRepository ingredientRepository;
	
	public IngredientByIdConverter(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}
	
	@Override
	public Ingredient convert(String id) {
		return ingredientRepository.findById(id);
	}

}