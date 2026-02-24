package com.riyatuition.riya_tuition.serviceImgl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.riyatuition.riya_tuition.entity.AdminEntity;
import com.riyatuition.riya_tuition.repository.AdminRepository;
import com.riyatuition.riya_tuition.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${supabase.url}")
    private String SUPABASE_URL;

    @Value("${supabase.service-key}")
    private String SUPABASE_SERVICE_KEY;

    public AdminServiceImpl(AdminRepository repo) {
        this.repo = repo;
    }

    // ✅ REGISTER
    @Override
    public String register(String name, String email, String phone,
            String password, MultipartFile image) {

        if (repo.findByEmail(email).isPresent()) {
            return "Email already exists";
        }

        String imageUrl = uploadToSupabase(image, 0);

        AdminEntity admin = new AdminEntity();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setPassword(encoder.encode(password));
        admin.setImage(imageUrl); // ✅ FULL Supabase URL

        repo.save(admin);
        return "Admin registered successfully";
    }

    // ✅ UPDATE
    @Override
    public String update(Integer id, String name, String email, String phone,
            MultipartFile image) {

        AdminEntity admin = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setName(name);
        admin.setEmail(email);
        admin.setPhone(phone);

        if (image != null && !image.isEmpty()) {
            // Pass admin ID to upload method
            admin.setImage(uploadToSupabase(image, id));
        }

        repo.save(admin);
        return "Admin updated successfully";
    }

    // ✅ DELETE
    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    // ✅ GET ALL
    @Override
    public List<AdminEntity> getAll() {
        return repo.findAll();
    }

    // ✅ GET BY ID
    @Override
    public AdminEntity getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @Override
    public String changePassword(Integer id, String oldPassword, String newPassword, String confirmPassword) {
        AdminEntity admin = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        admin.setPassword(encoder.encode(newPassword));
        admin.setConfirmPassword(encoder.encode(confirmPassword)); // Or keep as is, usually we store encoded or just
                                                                   // password
        repo.save(admin);

        return "Password changed successfully";
    }

    // 🔹 SUPABASE IMAGE UPLOAD (CORE METHOD)

    private String uploadToSupabase(MultipartFile file, Integer adminId) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            String fileName = "admin_" + adminId + "_" + System.currentTimeMillis() + ".png";

            String uploadUrl = SUPABASE_URL + "/storage/v1/object/admin-images/" + fileName;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setBearerAuth(SUPABASE_SERVICE_KEY);

            HttpEntity<byte[]> request = new HttpEntity<>(file.getBytes(), headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange(uploadUrl, HttpMethod.PUT, request, String.class);

            return SUPABASE_URL
                    + "/storage/v1/object/public/admin-images/"
                    + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Supabase image upload failed", e);
        }
    }

}
