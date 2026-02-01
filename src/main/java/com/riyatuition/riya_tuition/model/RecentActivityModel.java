package com.riyatuition.riya_tuition.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor   // ✅ THIS IS IMPORTANT
public class RecentActivityModel {

    private String type;        // STUDENT / PAYMENT / ATTENDANCE
    private String title;       // New student added / Payment received / Absent
    private String studentName; // Student name
    private BigDecimal amount;  // Only for payment (else null)
    private LocalDateTime createdDate;
}
