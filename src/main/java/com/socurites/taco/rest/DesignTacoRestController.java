package com.socurites.taco.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socurites.taco.model.Taco;
import com.socurites.taco.repository.TacoRepository;

@RestController
@RequestMapping(path="/rest/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoRestController {
	private final TacoRepository tacoRepository;
	private static final Logger log = LoggerFactory.getLogger(DesignTacoRestController.class);
	
	@Autowired
	private DesignTacoRestController(TacoRepository tacoRepository) {
		this.tacoRepository = tacoRepository;
	}
	
	@GetMapping("/recent")
	public Iterable<Taco> recentTacos() {
		PageRequest page = PageRequest.of(0,  12,
				Sort.by("createdAt").descending());
		
		return tacoRepository.findAll(page).getContent();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optional = tacoRepository.findById(id);
		
		if (optional.isPresent()) {
			return new ResponseEntity<>(optional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) {
		return tacoRepository.save(taco);
	}
	
	@PutMapping("/{id}")
	public Taco putTaco(@RequestBody Taco taco) {
		return tacoRepository.save(taco);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Taco> patchTaco(@PathVariable("id") Long id,
			@RequestBody Taco patch) {
		Optional<Taco> optional = tacoRepository.findById(id);
		
		Taco taco = null;
		if (!optional.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			taco = optional.get();
		}
		
		if (null != patch.getName()) {
			taco.setName(patch.getName());
		}
		
		if (null != patch.getIngredients()) {
			taco.setIngredients(patch.getIngredients());
		}
		
		Taco saved = tacoRepository.save(taco);
		
		return new ResponseEntity<>(saved, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteTaco(@PathVariable("id") Long id) {
		try {
			tacoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			log.warn(String.format("No taco for id: %d", id), e);
		}
	}
	
}
