package com.riyatuition.riya_tuition.serviceImgl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.riyatuition.riya_tuition.repository.AdminRepository;
import com.riyatuition.riya_tuition.entity.AdminEntity;
import com.riyatuition.riya_tuition.model.AdminLoginModel;
import com.riyatuition.riya_tuition.model.AdminLoginResponse;
import com.riyatuition.riya_tuition.service.AdminAuthService;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AdminAuthServiceImpl(AdminRepository repo) {
        this.repo = repo;
    }

    

    @Override
    public AdminLoginResponse login(AdminLoginModel request) {

        AdminEntity admin = repo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email")
                );

        if (!encoder.matches(request.getPassword(), admin.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        AdminLoginResponse response = new AdminLoginResponse();
        response.setId(admin.getId());
        response.setName(admin.getName());
        response.setEmail(admin.getEmail());
        response.setPhone(admin.getPhone());
        response.setImage(admin.getImage());

        return response;
    }

}
