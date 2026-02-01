package com.riyatuition.riya_tuition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.riyatuition.riya_tuition.model.StandardModel;
import com.riyatuition.riya_tuition.service.StandardService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/standard")
public class StandardController {

	@Autowired
	private StandardService service;

	@PostMapping("/create")
	public StandardModel createStandard(@Valid @RequestBody StandardModel model) {
		return service.createStandard(model);
	}

	@GetMapping("/all")
	public List<StandardModel> getAll() {
		return service.getAllStandard();
	}

	@GetMapping("/{id}")
	public StandardModel getById (@Parameter(description = "Standard ID") @PathVariable("id") Integer id) {
		return service.getStandardByid(id);
	}

	@PutMapping("/update/{id}")
	public StandardModel update(@Parameter(description = "Standard ID") @PathVariable("id") Integer id,
			@RequestBody @Valid StandardModel model) {
		return service.updateStandard(id, model);
	}

	@DeleteMapping("/delete/{id}")
	public String delete (@Parameter(description = "Standard ID") @PathVariable("id") Integer id) {
		return service.deleteStandard(id);
	}
}
