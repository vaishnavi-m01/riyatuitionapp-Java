package com.riyatuition.riya_tuition.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riyatuition.riya_tuition.entity.ClassFeesEntity;

public interface ClassFeesRepository extends JpaRepository<ClassFeesEntity, Integer> {
	
	Optional<ClassFeesEntity> findByStandardId(Integer standardId);
    Optional<ClassFeesEntity> findByIdAndStandardId(Integer id, Integer standardId);


	
}
