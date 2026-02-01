package com.riyatuition.riya_tuition.serviceImgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.repository.StudentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchReportService {

    private final StudentRepo studentRepository;

    public List<Map<String, Object>> getBatchDistribution() {

        List<Object[]> data = studentRepository.getBatchDistribution();

        return data.stream().map(obj -> {
            Map<String, Object> map = new HashMap<>();
            map.put("className", obj[0]);
            map.put("students", obj[1]);
            return map;
        }).toList();
    }
}

