package com.riyatuition.riya_tuition.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.riyatuition.riya_tuition.entity.StudentEntity;

public interface StudentRepo extends JpaRepository<StudentEntity, Integer> {
	
	 @Query("""
		        SELECT s.standard.name, COUNT(s.id)
		        FROM StudentEntity s
		        WHERE s.activeStatus = true
		        GROUP BY s.standard.name
		    """)
		    List<Object[]> getBatchDistribution();
		    
		    long countBy();
		    long count();

		    
		    // New students this month
		    @Query("""
		        SELECT COUNT(s) 
		        FROM StudentEntity s 
		        WHERE MONTH(s.createdDate) = MONTH(CURRENT_DATE)
		        AND YEAR(s.createdDate) = YEAR(CURRENT_DATE)
		    """)
		    long countNewStudentsThisMonth();

		    // Birthdays today
		    @Query("""
		        SELECT COUNT(s)
		        FROM StudentEntity s
		        WHERE DAY(s.dateOfBirth) = DAY(CURRENT_DATE)
		        AND MONTH(s.dateOfBirth) = MONTH(CURRENT_DATE)
		    """)
		    long countTodayBirthdays();
		    
		    Optional<StudentEntity> findTopByCreatedDateBetweenOrderByCreatedDateDesc(
		    	    LocalDateTime start,
		    	    LocalDateTime end
		    	);



}
