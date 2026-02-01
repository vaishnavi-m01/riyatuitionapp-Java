package com.riyatuition.riya_tuition.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.riyatuition.riya_tuition.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {

    Optional<AdminEntity> findByEmail(String email);
}
