package com.riyatuition.riya_tuition.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;

import com.riyatuition.riya_tuition.entity.AdminEntity;
import com.riyatuition.riya_tuition.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    // ✅ REGISTER
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> register(
            @RequestPart("name") String name,
            @RequestPart("email") String email,
            @RequestPart("phone") String phone,
            @RequestPart("password") String password,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            String result = service.register(name, email, phone, password, image);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace(); // Will log error in Render logs
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // ✅ UPDATE
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> update(
            @PathVariable Integer id,
            @RequestPart(value = "name", required = false) String name,
            @RequestPart(value = "email", required = false) String email,
            @RequestPart(value = "phone", required = false) String phone,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(
                service.update(id, name, email, phone, image));
    }

    // ✅ DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    // ✅ GET ALL (NO IMAGE MODIFICATION)
    @GetMapping("/getall")
    public ResponseEntity<List<AdminEntity>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // ✅ GET BY ID
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<AdminEntity> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        String result = service.changePassword(request.getId(), request.getOldPassword(), request.getNewPassword(),
                request.getConfirmPassword());
        return ResponseEntity.ok(result);
    }

    // Inner class for request body
    public static class ChangePasswordRequest {
        private Integer id;
        private String oldPassword;
        private String newPassword;
        private String confirmPassword;

        // Getters and Setters
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }
}
