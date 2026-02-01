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
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return ResponseEntity.ok(
                service.register(name, email, phone, password, image)
        );
    }

    // ✅ UPDATE
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> update(
            @PathVariable Integer id,
            @RequestPart(value = "name", required = false) String name,
            @RequestPart(value = "phone", required = false) String phone,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return ResponseEntity.ok(
                service.update(id, name, phone, image)
        );
    }

    // ✅ DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    // ✅ GET ALL (FIXED IMAGE URL)
    @GetMapping("/getall")
    public ResponseEntity<List<AdminEntity>> getAll(HttpServletRequest request) {

        List<AdminEntity> admins = service.getAll();

        String baseUrl = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort();

        for (AdminEntity admin : admins) {
            if (admin.getImage() != null && !admin.getImage().isBlank()) {
                admin.setImage(baseUrl + "/api/" + admin.getImage());
            }
        }

        return ResponseEntity.ok(admins);
    }

    // ✅ GET BY ID (FIXED IMAGE URL)
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<AdminEntity> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {

        AdminEntity admin = service.getById(id);

        if (admin.getImage() != null && !admin.getImage().isBlank()) {
            String baseUrl = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort();

            admin.setImage(baseUrl + "/api/" + admin.getImage());
        }

        return ResponseEntity.ok(admin);
    }
}
