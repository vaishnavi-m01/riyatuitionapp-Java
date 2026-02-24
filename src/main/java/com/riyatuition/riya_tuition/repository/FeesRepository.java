package com.riyatuition.riya_tuition.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.riyatuition.riya_tuition.entity.FeesEntity;
import com.riyatuition.riya_tuition.enums.PaymentType;

@Repository
public interface FeesRepository extends JpaRepository<FeesEntity, Integer> {

	@Query("""
			    SELECT
			        COALESCE(SUM(f.paid), 0),
			        COALESCE(SUM(CASE WHEN f.paymentType = :cash THEN f.paid ELSE 0 END), 0),
			        COALESCE(SUM(CASE WHEN f.paymentType = :upi THEN f.paid ELSE 0 END), 0)
			    FROM FeesEntity f
			    WHERE f.createdDate BETWEEN :start AND :end
			""")
	Object[] getPaidSummary(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("cash") PaymentType cash,
			@Param("upi") PaymentType upi);

	// Total pending amount (was f.balance -> f.pending)
	@Query("""
			    SELECT COALESCE(SUM(f.pending), 0)
			    FROM FeesEntity f
			    WHERE f.pending > 0
			""")
	BigDecimal totalDueAmount();

	// Students with pending fees
	@Query("""
			    SELECT COUNT(DISTINCT f.studentId)
			    FROM FeesEntity f
			    WHERE f.pending > 0
			""")
	long countDueStudents();

	@Query("SELECT COALESCE(SUM(f.paid),0) FROM FeesEntity f WHERE f.createdDate BETWEEN :start AND :end")
	Integer sumPaidByCreatedDateBetween(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end);

	Optional<FeesEntity> findTopByCreatedDateBetweenOrderByCreatedDateDesc(
			LocalDateTime start,
			LocalDateTime end);

	List<FeesEntity> findByCreatedDateBetween(
			LocalDateTime start,
			LocalDateTime end);

	@Query("""
			    SELECT COUNT(DISTINCT f.studentId)
			    FROM FeesEntity f
			    WHERE f.createdDate BETWEEN :start AND :end
			    AND f.status = 'Paid'
			""")
	long countUniquePaidStudents(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	@Query("""
			    SELECT DISTINCT s.name
			    FROM FeesEntity f, StudentEntity s
			    WHERE f.studentId = s.id
			    AND f.createdDate BETWEEN :start AND :end
			    AND f.status = 'Paid'
			""")
	List<String> findPaidStudentNames(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	@Query("""
			    SELECT COALESCE(SUM(COALESCE(f.paid, 0) + COALESCE(f.pending, 0)), 0) FROM FeesEntity f
			    WHERE (:month IS NULL OR EXTRACT(MONTH FROM f.createdDate) = :month)
			    AND (:year IS NULL OR EXTRACT(YEAR FROM f.createdDate) = :year)
			""")
	BigDecimal sumTotalAmount(@Param("month") Integer month, @Param("year") Integer year);

	@Query("""
			    SELECT COALESCE(SUM(f.paid), 0) FROM FeesEntity f
			    WHERE (:month IS NULL OR EXTRACT(MONTH FROM f.createdDate) = :month)
			    AND (:year IS NULL OR EXTRACT(YEAR FROM f.createdDate) = :year)
			""")
	BigDecimal sumTotalPaid(@Param("month") Integer month, @Param("year") Integer year);

	@Query("""
			    SELECT COUNT(DISTINCT f.studentId) FROM FeesEntity f
			    WHERE (:month IS NULL OR EXTRACT(MONTH FROM f.createdDate) = :month)
			    AND (:year IS NULL OR EXTRACT(YEAR FROM f.createdDate) = :year)
			    AND f.status = 'Paid'
			""")
	long countPaidStudents(@Param("month") Integer month, @Param("year") Integer year);

	@Query("""
			    SELECT COUNT(DISTINCT f.studentId) FROM FeesEntity f
			    WHERE (:month IS NULL OR EXTRACT(MONTH FROM f.createdDate) = :month)
			    AND (:year IS NULL OR EXTRACT(YEAR FROM f.createdDate) = :year)
			    AND f.status = 'Pending'
			""")
	long countPendingStudents(@Param("month") Integer month, @Param("year") Integer year);

}
