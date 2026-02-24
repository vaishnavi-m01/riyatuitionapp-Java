package com.riyatuition.riya_tuition.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.riyatuition.riya_tuition.entity.AdminEntity;

public interface AdminService {

        String register(String name, String email, String phone,
                        String password, MultipartFile image);

        String update(Integer id, String name, String email, String phone,
                        MultipartFile image);

        void delete(Integer id);

        List<AdminEntity> getAll();

        AdminEntity getById(Integer id);

        String changePassword(Integer id, String oldPassword, String newPassword, String confirmPassword);
}
