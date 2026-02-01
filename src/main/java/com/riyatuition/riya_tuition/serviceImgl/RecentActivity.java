package com.riyatuition.riya_tuition.serviceImgl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.entity.AttendanceEntity;
import com.riyatuition.riya_tuition.entity.FeesEntity;
import com.riyatuition.riya_tuition.entity.StudentEntity;
import com.riyatuition.riya_tuition.enums.AttendanceStatus;
import com.riyatuition.riya_tuition.model.RecentActivityModel;
import com.riyatuition.riya_tuition.repository.AttendanceRepository;
import com.riyatuition.riya_tuition.repository.FeesRepository;
import com.riyatuition.riya_tuition.repository.StudentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentActivity {

    private final StudentRepo studentRepo;
    private final FeesRepository feesRepo;
    private final AttendanceRepository attendanceRepo;

    public List<RecentActivityModel> getRecentActivities() {

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        List<RecentActivityModel> result = new ArrayList<>();

        /* =====================
           1️⃣ STUDENT (ONLY LATEST – TODAY)
           ===================== */
        studentRepo
            .findTopByCreatedDateBetweenOrderByCreatedDateDesc(start, end)
            .ifPresent(s -> result.add(
                new RecentActivityModel(
                    "STUDENT",
                    "New student added",
                    s.getName(),
                    null,
                    s.getCreatedDate()
                )
            ));

        /* =====================
           2️⃣ PAYMENTS (TODAY)
           ===================== */
        List<FeesEntity> payments = feesRepo.findByCreatedDateBetween(start, end);

        if (!payments.isEmpty()) {

            if (payments.size() == 1) {
                FeesEntity f = payments.get(0);

                String name = studentRepo.findById(f.getStudentId())
                        .map(StudentEntity::getName)
                        .orElse("Student");

                result.add(new RecentActivityModel(
                    "PAYMENT",
                    "Payment received",
                    name,
                    f.getPaid(),
                    f.getCreatedDate()
                ));

            } else {
                BigDecimal total = payments.stream()
                        .map(FeesEntity::getPaid)
                        .filter(p -> p != null)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                LocalDateTime latestPaymentTime = payments.stream()
                        .map(FeesEntity::getCreatedDate)
                        .filter(d -> d != null)
                        .max(LocalDateTime::compareTo)
                        .orElse(start);

                result.add(new RecentActivityModel(
                    "PAYMENT",
                    "Payments received",
                    payments.size() + " students today",
                    total,
                    latestPaymentTime
                ));
            }
        }

        /* =====================
           3️⃣ ATTENDANCE (TODAY – ABSENT)
           ===================== */
        List<AttendanceEntity> absents =
                attendanceRepo.findByDateAndStatus(today, AttendanceStatus.Absent);

        if (!absents.isEmpty()) {

            if (absents.size() == 1) {
                AttendanceEntity a = absents.get(0);

                String name = a.getStudent() != null
                        ? a.getStudent().getName()
                        : "Student";

                result.add(new RecentActivityModel(
                    "ATTENDANCE",
                    "Attendance alert",
                    name + " Absent",
                    null,
                    a.getCreatedDate()
                ));

            } else {
                LocalDateTime latestAttendanceTime = absents.stream()
                        .map(AttendanceEntity::getCreatedDate)
                        .filter(d -> d != null)
                        .max(LocalDateTime::compareTo)
                        .orElse(start);

                result.add(new RecentActivityModel(
                    "ATTENDANCE",
                    "Attendance alert",
                    absents.size() + " students absent today",
                    null,
                    latestAttendanceTime
                ));
            }
        }

        /* =====================
           SORT (NULL SAFE) + LIMIT
           ===================== */
        return result.stream()
                .filter(r -> r.getCreatedDate() != null)
                .sorted((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()))
                .limit(4)
                .toList();
    }
}

