package com.riyatuition.riya_tuition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.riyatuition.riya_tuition.model.StudentModel;
import com.riyatuition.riya_tuition.service.StudentService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService service;

	@PostMapping("/create")
	public StudentModel createStudent(@Valid @RequestBody StudentModel model) {
		return service.createStudent(model);
	}

	@GetMapping("/all")
	public List<StudentModel> getAll() {
		return service.getAllStudents();
	}

	@GetMapping("/{id}")
	public StudentModel getById (@Parameter(description = "Student ID") @PathVariable("id") Integer id) {
		return service.getStudentById(id);
	}

	@PutMapping("/update/{id}")
	public StudentModel update(@Parameter(description = "Student ID") @PathVariable("id") Integer id,
			@RequestBody @Valid StudentModel model) {
		return service.updateStudent(id, model);
	}

	@DeleteMapping("/delete/{id}")
	public String delete (@Parameter(description = "Student ID") @PathVariable("id") Integer id) {
		return service.deleteStudent(id);
	}
}
