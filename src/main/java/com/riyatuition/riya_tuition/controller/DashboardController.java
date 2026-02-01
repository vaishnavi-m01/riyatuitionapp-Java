package com.riyatuition.riya_tuition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riyatuition.riya_tuition.model.DashboardModel;
import com.riyatuition.riya_tuition.serviceImgl.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardModel> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }
}

