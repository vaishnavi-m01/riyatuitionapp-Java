package com.riyatuition.riya_tuition.service;

import java.util.List;
import com.riyatuition.riya_tuition.model.StudentModel;

public interface StudentService {

    StudentModel createStudent(StudentModel model);

    List<StudentModel> getAllStudents();

    StudentModel getStudentById(Integer id);

    StudentModel updateStudent(Integer id, StudentModel model);

    String deleteStudent(Integer id);
}
