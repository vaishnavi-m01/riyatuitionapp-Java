package com.riyatuition.riya_tuition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riyatuition.riya_tuition.serviceImgl.BatchReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class BatchReportController {

    private final BatchReportService batchReportService;

    @GetMapping("/batch-distribution")
    public ResponseEntity<?> getBatchDistribution() {
        return ResponseEntity.ok(
                batchReportService.getBatchDistribution()
        );
    }
}
