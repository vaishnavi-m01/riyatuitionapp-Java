package com.riyatuition.riya_tuition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.riyatuition.riya_tuition.entity.StandardEntity;

public interface StandardRepo extends JpaRepository<StandardEntity, Integer> {
}
