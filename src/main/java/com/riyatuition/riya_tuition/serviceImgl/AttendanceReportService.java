package com.riyatuition.riya_tuition.serviceImgl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.repository.AttendanceRepository;
import com.riyatuition.riya_tuition.repository.StudentRepo;
import com.riyatuition.riya_tuition.utils.DateRangeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceReportService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepo studentRepo;

    public List<Map<String, Object>> getAttendanceTrends(String period) {

        LocalDateTime[] range = DateRangeUtil.getDateRange(period);

        long totalStudents = studentRepo.countBy(); 

        List<Object[]> data = attendanceRepository.getAttendanceTrends(
                range[0].toLocalDate(),
                range[1].toLocalDate()
        );

        return data.stream().map(obj -> {
            LocalDate date = (LocalDate) obj[0];
            int present = ((Long) obj[1]).intValue();

            Map<String, Object> map = new HashMap<>();
            map.put("date", date);
            map.put("present", present);
            map.put("total", totalStudents);
            map.put(
                "percentage",
                totalStudents == 0 ? 0 : (present * 100) / totalStudents
            );

            return map;
        }).toList();
    }
}


