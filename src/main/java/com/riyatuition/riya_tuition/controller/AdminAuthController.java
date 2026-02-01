package com.riyatuition.riya_tuition.controller;

import org.springframework.web.bind.annotation.*;

import com.riyatuition.riya_tuition.model.AdminLoginModel;
import com.riyatuition.riya_tuition.model.AdminLoginResponse;
import com.riyatuition.riya_tuition.service.AdminAuthService;

@RestController
@RequestMapping("/auth")
public class AdminAuthController {

    private final AdminAuthService authService;

    public AdminAuthController(AdminAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AdminLoginResponse login(@RequestBody AdminLoginModel request) {
        return authService.login(request);
    }
}

