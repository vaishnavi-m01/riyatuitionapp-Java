package com.riyatuition.riya_tuition.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riyatuition.riya_tuition.serviceImgl.FeesReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class FeesReportController {

    private final FeesReportService feesReportService;

    @GetMapping("/fees-summary")
    public ResponseEntity<?> getFeesSummary(
            @RequestParam(value ="period") String period,
            @RequestParam(value="paymentMethod",defaultValue = "all") String paymentMethod
    ) {
        return ResponseEntity.ok(
                feesReportService.getFeesSummary(period, paymentMethod)
        );
    }


}
