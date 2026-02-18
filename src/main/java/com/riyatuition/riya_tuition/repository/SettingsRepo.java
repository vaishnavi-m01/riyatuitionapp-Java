package com.riyatuition.riya_tuition.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.riyatuition.riya_tuition.entity.SettingsEntity;

public interface SettingsRepo extends JpaRepository<SettingsEntity, Integer> {
    Optional<SettingsEntity> findByConfigKey(String configKey);
}
