package com.riyatuition.riya_tuition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.riyatuition.riya_tuition.entity.SettingsEntity;
import com.riyatuition.riya_tuition.repository.SettingsRepo;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsRepo settingsRepo;

    @GetMapping("/all")
    public List<SettingsEntity> getAll() {
        return settingsRepo.findAll();
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<SettingsEntity> getByKey(@PathVariable String key) {
        return settingsRepo.findByConfigKey(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public SettingsEntity save(@RequestBody SettingsEntity settings) {
        return settingsRepo.findByConfigKey(settings.getConfigKey())
                .map(existing -> {
                    existing.setConfigValue(settings.getConfigValue());
                    return settingsRepo.save(existing);
                })
                .orElseGet(() -> settingsRepo.save(settings));
    }
}
