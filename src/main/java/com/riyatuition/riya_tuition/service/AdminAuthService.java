package com.riyatuition.riya_tuition.service;

import com.riyatuition.riya_tuition.model.AdminLoginModel;
import com.riyatuition.riya_tuition.model.AdminLoginResponse;

public interface AdminAuthService {

    AdminLoginResponse login(AdminLoginModel request);
}
