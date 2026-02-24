package com.riyatuition.riya_tuition.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardModel {

    private long totalStudents;
    private long newStudentsThisMonth;

    private long presentToday;
    private long absentToday;

    private BigDecimal totalFeesDue;
    private long dueStudentsCount;

    private long paidStudentsThisMonth;
    private long pendingStudentsThisMonth;

    private long todayBirthdays;
}
