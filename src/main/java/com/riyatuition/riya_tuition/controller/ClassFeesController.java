package com.riyatuition.riya_tuition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.riyatuition.riya_tuition.model.ClassFeesModel;
import com.riyatuition.riya_tuition.service.ClassFeesService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/classFees")
public class ClassFeesController {

	@Autowired
	private ClassFeesService service;

	@PostMapping("/create")
	public ClassFeesModel createClassFees(@Valid @RequestBody ClassFeesModel model) {
		return service.createClassFees(model);
	}

	@GetMapping("/all")
	public List<ClassFeesModel> getAll() {
		return service.getAllClassFees();
	}

	
	
	
	@GetMapping
	public ClassFeesModel getFees(

	        @Parameter(
	            description = "Class Fees ID (optional)"
	        )
	        @RequestParam(name = "id", required = false)
	        Integer id,

	        @Parameter(
	            description = "Standard ID (optional)"
	        )
	        @RequestParam(name = "standardId", required = false)
	        Integer standardId
	) {

	    if (id != null && standardId != null) {
	        return service.getByIdAndStandardId(id, standardId);
	    } 
	    else if (id != null) {
	        return service.getById(id);
	    } 
	    else if (standardId != null) {
	        return service.getByStandardId(standardId);
	    }

	    throw new ResponseStatusException(
	            HttpStatus.BAD_REQUEST,
	            "Either id or standardId must be provided"
	    );
	}





	@PutMapping("/update/{id}")
	public ClassFeesModel update(@Parameter(description = "classFees ID") @PathVariable("id") Integer id,
			@RequestBody @Valid ClassFeesModel model) {
		return service.updateClassFees(id, model);
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@Parameter(description = "Student ID") @PathVariable("id") Integer id) {
		return service.deleteClassFees(id);
	}
}
