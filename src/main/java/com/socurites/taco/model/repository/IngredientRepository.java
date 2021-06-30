package com.socurites.taco.model.repository;

import com.socurites.taco.model.Ingredient;

public interface IngredientRepository {
	Iterable<Ingredient> findAll();
	
	Ingredient findById(String id);
	
	Ingredient save(Ingredient ingredient);
}
