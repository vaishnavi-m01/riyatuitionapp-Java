package com.riyatuition.riya_tuition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.riyatuition.riya_tuition.model.StudentModel;
import com.riyatuition.riya_tuition.service.StudentService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    // ✅ CREATE STUDENT WITH IMAGE
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentModel createStudent(
            @ModelAttribute StudentModel model,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return service.createStudent(model, image);
    }

    // ✅ UPDATE STUDENT IMAGE (overwrite)
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentModel updateStudent(
            @PathVariable Integer id,
            @ModelAttribute @Valid StudentModel model,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return service.updateStudent(id, model, image);
    }

    @GetMapping("/all")
    public List<StudentModel> getAll() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentModel getStudentById(@PathVariable Integer id) {
        return service.getStudentById(id);
    }

    @GetMapping("/birthdays/today")
    public List<StudentModel> getTodayBirthdays() {
        return service.getTodayBirthdays();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        return service.deleteStudent(id);
    }
}
