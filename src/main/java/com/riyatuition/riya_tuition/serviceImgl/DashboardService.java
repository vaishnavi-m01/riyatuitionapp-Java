package com.riyatuition.riya_tuition.serviceImgl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.enums.AttendanceStatus;
import com.riyatuition.riya_tuition.model.DashboardModel;
import com.riyatuition.riya_tuition.repository.AttendanceRepository;
import com.riyatuition.riya_tuition.repository.FeesRepository;
import com.riyatuition.riya_tuition.repository.StudentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepo studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final FeesRepository feesRepository;

    public DashboardModel getDashboardData() {

        LocalDate today = LocalDate.now();
        LocalDateTime startOfMonth = getStartOfMonth();
        LocalDateTime endOfMonth = getEndOfMonth();

        long totalStudents = studentRepository.count();
        long paidStudentsThisMonth = feesRepository.countUniquePaidStudents(startOfMonth, endOfMonth);
        long pendingStudentsThisMonth = totalStudents - paidStudentsThisMonth;

        return new DashboardModel(
                studentRepository.count(),
                studentRepository.countNewStudentsThisMonth(),

                attendanceRepository.countByDateAndStatus(today, AttendanceStatus.Present),
                attendanceRepository.countByDateAndStatus(today, AttendanceStatus.Absent),

                feesRepository.totalDueAmount(),
                feesRepository.countDueStudents(),

                paidStudentsThisMonth,
                pendingStudentsThisMonth,

                studentRepository.countTodayBirthdays());
    }

    private LocalDateTime getStartOfMonth() {
        return LocalDate.now().withDayOfMonth(1).atStartOfDay();
    }

    private LocalDateTime getEndOfMonth() {
        return LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().minusNanos(1);
    }
}
