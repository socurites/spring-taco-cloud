package com.socurites.taco.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.socurites.taco.model.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>{
	Taco save(Taco taco);
}
