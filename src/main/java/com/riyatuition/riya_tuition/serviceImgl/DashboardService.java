package com.riyatuition.riya_tuition.serviceImgl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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
        List<String> paidStudentNames = feesRepository.findPaidStudentNames(startOfMonth, endOfMonth);
        long paidStudentsThisMonth = paidStudentNames.size();
        long pendingStudentsThisMonth = totalStudents - paidStudentsThisMonth;

        List<String> allStudentNames = studentRepository.findAll().stream()
                .map(s -> s.getName())
                .collect(Collectors.toList());

        List<String> pendingStudentNames = allStudentNames.stream()
                .filter(name -> !paidStudentNames.contains(name))
                .collect(Collectors.toList());

        return new DashboardModel(
                studentRepository.count(),
                studentRepository.countNewStudentsThisMonth(),

                attendanceRepository.countByDateAndStatus(today, AttendanceStatus.Present),
                attendanceRepository.countByDateAndStatus(today, AttendanceStatus.Absent),

                feesRepository.totalDueAmount(),
                feesRepository.countDueStudents(),

                paidStudentsThisMonth,
                pendingStudentsThisMonth,

                paidStudentNames,
                pendingStudentNames,

                studentRepository.countTodayBirthdays());
    }

    private LocalDateTime getStartOfMonth() {
        return LocalDate.now().withDayOfMonth(1).atStartOfDay();
    }

    private LocalDateTime getEndOfMonth() {
        return LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().minusNanos(1);
    }
}
