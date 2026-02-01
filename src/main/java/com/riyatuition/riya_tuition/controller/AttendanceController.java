
package com.riyatuition.riya_tuition.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riyatuition.riya_tuition.model.AttendanceModel;
import com.riyatuition.riya_tuition.model.AttendanceResponse;
import com.riyatuition.riya_tuition.service.AttendanceService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	private final AttendanceService service;

	public AttendanceController(AttendanceService service) {
		this.service = service;
	}

	// CREATE
	@PostMapping
	public AttendanceModel create(@Valid @RequestBody AttendanceModel model) {
		return service.create(model);
	}

//    // GET ALL
//    @GetMapping
//    public List<AttendanceModel> getAll() {
//        return service.getAll();
//    }

	@GetMapping
	public AttendanceResponse getAll(
			@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

		return service.getAll(date);
	}

	// GET BY ID
	@GetMapping("/{id}")
	public AttendanceModel getById(@Parameter(description = "id") @PathVariable("id") Integer id) {

		return service.getById(id);
	}

	// UPDATE
	@PutMapping("/{id}")
	public AttendanceModel update(@Parameter(description = "id") @PathVariable("id") Integer id,
			@Valid @RequestBody AttendanceModel model) {

		return service.update(id, model);
	}

	// DELETE
	@DeleteMapping("/{id}")
	public String delete(@Parameter(description = "id") @PathVariable("id") Integer id) {

		service.delete(id);
		return "Attendance deleted successfully";
	}
}
