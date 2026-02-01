package com.riyatuition.riya_tuition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riyatuition.riya_tuition.serviceImgl.AttendanceReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class AttendanceReportController {

    private final AttendanceReportService attendanceReportService;

    @GetMapping("/attendance-trends")
    public ResponseEntity<?> getAttendanceTrends(
            @RequestParam (name ="period",defaultValue = "today")String period)
    {
        return ResponseEntity.ok(
                attendanceReportService.getAttendanceTrends(period)
        );
    }
}
