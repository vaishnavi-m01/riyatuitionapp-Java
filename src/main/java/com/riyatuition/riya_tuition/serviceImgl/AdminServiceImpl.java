package com.riyatuition.riya_tuition.serviceImgl;

import java.io.File;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.riyatuition.riya_tuition.entity.AdminEntity;
import com.riyatuition.riya_tuition.repository.AdminRepository;
import com.riyatuition.riya_tuition.service.AdminService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final String UPLOAD_DIR = "uploads/admin/";

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

        String imagePath = saveImage(image);

        AdminEntity admin = new AdminEntity();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setPassword(encoder.encode(password));
        admin.setImage(imagePath);

        repo.save(admin);
        return "Admin registered successfully";
    }

    // ✅ UPDATE
    @Override
    public String update(Integer id, String name, String phone,
                         MultipartFile image) {

        AdminEntity admin = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setName(name);
        admin.setPhone(phone);

        if (image != null && !image.isEmpty()) {
            admin.setImage(saveImage(image));
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

    // 🔹 Image Save Helper
    private String saveImage(MultipartFile image) {

        if (image == null || image.isEmpty()) {
            return null;
        }

        try {
            String uploadDir = "E:/Backend-Java/riya-tuition/uploads/admin/";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = image.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(
                image.getInputStream(),
                filePath,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            return "uploads/admin/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Image upload failed");
        }
    }


}
