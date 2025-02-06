package com.naitech.cursomc.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.dto.CategoryDTO;
import com.naitech.cursomc.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<Category> categories = categoryService.findAll();

		List<CategoryDTO> categoriesDTO = categories.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());

		return ResponseEntity.ok().body(categoriesDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Integer id) {
		Category category = categoryService.find(id);

		return ResponseEntity.ok().body(category);
	}

	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody CategoryDTO categoryDTO) {
		Category category = categoryService.fromDTO(categoryDTO);
		category = categoryService.insert(category);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryDTO.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO) {
		Category category = categoryService.fromDTO(categoryDTO);
		category.setId(id);
		category = categoryService.update(category);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		categoryService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoryDTO>> findAllPagination(@RequestParam(defaultValue = "0") int page, // Default to
																											// page 0
			@RequestParam(defaultValue = "24") int size, // Default size 10
			@RequestParam(defaultValue = "id,asc") String[] sort // Default sorting
	) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
		Page<Category> categories = categoryService.findPage(pageable);
		Page<CategoryDTO> categoriesDTO = categories.map(c -> new CategoryDTO(c));
		return ResponseEntity.ok(categoriesDTO);
	}

	private Sort.Order parseSort(String[] sort) {
		return new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
	}
}
