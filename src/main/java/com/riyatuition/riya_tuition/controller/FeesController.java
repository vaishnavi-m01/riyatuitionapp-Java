package com.riyatuition.riya_tuition.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.riyatuition.riya_tuition.model.FeesModel;
import com.riyatuition.riya_tuition.model.FeesSummaryModel;
import com.riyatuition.riya_tuition.service.FeesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fees")
@RequiredArgsConstructor
public class FeesController {

    private final FeesService feesService;

    @PostMapping("/create")
    public ResponseEntity<FeesModel> createFees(@RequestBody FeesModel model) {
        return ResponseEntity.ok(feesService.createFees(model));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FeesModel>> getAllFees() {
        return ResponseEntity.ok(feesService.getAllFees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeesModel> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(feesService.getFeesById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FeesModel> update(@PathVariable Integer id, @RequestBody FeesModel model) {
        return ResponseEntity.ok(feesService.updateFees(id, model));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(feesService.deleteFees(id));
    }

    @PostMapping("/settle")
    public ResponseEntity<FeesModel> settleBalance(@RequestBody FeesModel model) {
        return ResponseEntity.ok(feesService.settleBalance(model));
    }

    @GetMapping("/recent/{studentId}")
    public ResponseEntity<List<FeesModel>> getRecentTransactions(@PathVariable Integer studentId) {
        return ResponseEntity.ok(feesService.getRecentTransactions(studentId));
    }

    @GetMapping("/summary")
    public ResponseEntity<FeesSummaryModel> getSummary(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        return ResponseEntity.ok(feesService.getSummary(month, year));
    }
}
