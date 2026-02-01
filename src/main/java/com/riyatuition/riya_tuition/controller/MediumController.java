package com.riyatuition.riya_tuition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.riyatuition.riya_tuition.model.MediumModel;
import com.riyatuition.riya_tuition.service.MediumService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medium")
public class MediumController {

	@Autowired
	private MediumService service;

	@PostMapping("/create")
	public MediumModel createMedium(@Valid @RequestBody MediumModel model) {
		return service.createMedium(model);
	}

	@GetMapping("/all")
	public List<MediumModel> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public MediumModel getById(@Parameter(description = "medium Id") @PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@PutMapping("/update/{id}")
	public MediumModel update(@Parameter(description = "medium Id") @PathVariable("id") Integer id,
			@RequestBody @Valid MediumModel model) {
		return service.updateMedium(id, model);
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@Parameter(description = "medium Id") @PathVariable("id") Integer id) {
		return service.deleteMedium(id);
	}
}
