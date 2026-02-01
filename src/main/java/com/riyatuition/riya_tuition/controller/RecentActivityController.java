package com.riyatuition.riya_tuition.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riyatuition.riya_tuition.model.RecentActivityModel;
import com.riyatuition.riya_tuition.serviceImgl.RecentActivity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class RecentActivityController {

    private final RecentActivity dashboardService;

    @GetMapping("/recent-activities")
    public List<RecentActivityModel> recentActivities() {
        return dashboardService.getRecentActivities();
    }
}


