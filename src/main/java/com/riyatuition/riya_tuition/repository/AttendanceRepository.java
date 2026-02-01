package com.riyatuition.riya_tuition.repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.riyatuition.riya_tuition.entity.AttendanceEntity;
import com.riyatuition.riya_tuition.enums.AttendanceStatus;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {

    // Get all attendance records for a given date
    List<AttendanceEntity> findByDate(LocalDate date);

    // Get attendance record for a specific student on a specific date
    Optional<AttendanceEntity> findByStudentIdAndDate(Integer studentId, LocalDate date);

    // Count attendance for a given date and status (Present/Absent)
    long countByDateAndStatus(LocalDate date, AttendanceStatus status);

    // Get attendance trends (total present per day) between two dates
    @Query("""
        SELECT a.date,
               SUM(CASE WHEN a.status = com.riyatuition.riya_tuition.enums.AttendanceStatus.Present THEN 1 ELSE 0 END)
        FROM AttendanceEntity a
        WHERE a.date BETWEEN :fromDate AND :toDate
        GROUP BY a.date
        ORDER BY a.date
    """)
    List<Object[]> getAttendanceTrends(
        @Param("fromDate") LocalDate fromDate,
        @Param("toDate") LocalDate toDate
    );
    

    List<AttendanceEntity> findByDateAndStatus(
    	    LocalDate date,
    	    AttendanceStatus status
    	);


}
