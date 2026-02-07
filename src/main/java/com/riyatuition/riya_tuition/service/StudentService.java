package com.riyatuition.riya_tuition.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.riyatuition.riya_tuition.model.StudentModel;

public interface StudentService {

	  StudentModel createStudent(StudentModel model, MultipartFile image);

	    StudentModel updateStudent(Integer id, StudentModel model, MultipartFile image);

	    List<StudentModel> getAllStudents();

	    StudentModel getStudentById(Integer id);

	    String deleteStudent(Integer id);
}
