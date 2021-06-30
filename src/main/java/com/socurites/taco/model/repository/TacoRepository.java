package com.socurites.taco.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.socurites.taco.model.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long>{
	Taco save(Taco taco);
}
