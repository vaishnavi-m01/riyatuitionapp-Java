package com.riyatuition.riya_tuition.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.riyatuition.riya_tuition.enums.PaymentType;
import com.riyatuition.riya_tuition.model.FeesModel;
import com.riyatuition.riya_tuition.service.FeesService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/fees")
//@CrossOrigin(origins = "*")
public class FeesController {

	@Autowired
	private FeesService feesService;

	@PostMapping("/create")
	public FeesModel createFees(@RequestBody FeesModel model) {
		return feesService.createFees(model);
	}

	@GetMapping("/getAll")
	public List<FeesModel> getAllFees(
			@Parameter(description = "date") @RequestParam(value = "date", required = false) String date) {
		return feesService.getAll(date);
	}

	@GetMapping("/get/{id}")
	public FeesModel getById(@Parameter(description = "Fees Id", required = true) @PathVariable("id") Integer id) {
		return feesService.getById(id);
	}

	@PutMapping("/update/{id}")
	public FeesModel updateFees(@Parameter(description = "fees Id") @PathVariable("id") Integer id,
			@RequestBody FeesModel model) {
		return feesService.updateFees(id, model);
	}

	@PostMapping("/settle/{id}")
	public FeesModel settleBalance(
	        @PathVariable("id") Integer id,
	        @RequestParam("amount") BigDecimal paid,
	        @RequestParam("paymentType") PaymentType paymentType) {

	    return feesService.settleBalance(id, paid, paymentType);
	}



	@DeleteMapping("/delete/{id}")
	public String deleteFees(@Parameter(description = "fees id") @PathVariable("id") Integer id) {
		return feesService.deleteFees(id);
	}
}
