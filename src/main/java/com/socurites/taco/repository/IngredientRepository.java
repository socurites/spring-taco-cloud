package com.socurites.taco.repository;

import org.springframework.data.repository.CrudRepository;

import com.socurites.taco.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String>{
}
