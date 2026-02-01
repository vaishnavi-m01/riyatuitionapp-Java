package com.riyatuition.riya_tuition.serviceImgl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riyatuition.riya_tuition.entity.MediumEntity;
import com.riyatuition.riya_tuition.entity.StandardEntity;
import com.riyatuition.riya_tuition.entity.StudentEntity;
import com.riyatuition.riya_tuition.model.StudentModel;
import com.riyatuition.riya_tuition.repository.MediumRepo;
import com.riyatuition.riya_tuition.repository.StandardRepo;
import com.riyatuition.riya_tuition.repository.StudentRepo;
import com.riyatuition.riya_tuition.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private StandardRepo standardRepo;

	@Autowired
	private MediumRepo mediumRepo;

	private StudentModel mapToModel(StudentEntity entity) {
	    if (entity == null) return null;

	    StudentModel model = new StudentModel();
	    model.setId(entity.getId());
	    model.setName(entity.getName());
	    model.setPhone(entity.getPhone());
	    model.setSchool(entity.getSchool());

	    if (entity.getStandard() != null) {
	        model.setStandardId(entity.getStandard().getId());
	        model.setStandardName(entity.getStandard().getName());
	    }

	    if (entity.getMedium() != null) {
	        model.setMediumId(entity.getMedium().getId());
	        model.setMediumName(entity.getMedium().getName());
	    }

	    model.setAge(entity.getAge());
	    model.setGender(entity.getGender());
	    model.setDateOfBirth(entity.getDateOfBirth());
	    model.setAddress(entity.getAddress());
	    model.setJoiningDate(entity.getJoiningDate());
	    model.setActiveStatus(entity.getActiveStatus());
	    model.setCreatedDate(entity.getCreatedDate());
	    model.setModifiedDate(entity.getModifiedDate());

	    return model;
	}

	@Override
	public StudentModel createStudent(StudentModel model) {

	    // ✅ Validate Standard
	    StandardEntity standard = standardRepo.findById(model.getStandardId())
	            .orElseThrow(() -> new RuntimeException("Standard not found"));

	    // ✅ Validate Medium
	    MediumEntity medium = mediumRepo.findById(model.getMediumId())
	            .orElseThrow(() -> new RuntimeException("Medium not found"));

	    StudentEntity student = new StudentEntity();

	    student.setName(model.getName());
	    student.setSchool(model.getSchool());
	    student.setAge(model.getAge());
	    student.setGender(model.getGender());
	    student.setDateOfBirth(model.getDateOfBirth());
	    student.setPhone(model.getPhone());
	    student.setAddress(model.getAddress());
	    student.setJoiningDate(model.getJoiningDate());
	    student.setActiveStatus(model.getActiveStatus());
	    student.setCreatedDate(LocalDateTime.now());
	    student.setModifiedDate(LocalDateTime.now());

	    // ✅ set validated entities
	    student.setStandard(standard);
	    student.setMedium(medium);

	    StudentEntity savedStudent = studentRepo.save(student);
	    return mapToModel(savedStudent);
	}

	
	

	@Override
	public List<StudentModel> getAllStudents() {
		return studentRepo.findAll().stream().map(this::mapToModel).toList();
	}

	@Override
	public StudentModel getStudentById(Integer id) {
		StudentEntity student = studentRepo.findById(id).orElse(null);
		return mapToModel(student);
	}

	@Override
	public StudentModel updateStudent(Integer id, StudentModel model) {
		StudentEntity student = studentRepo.findById(id).orElse(null);

		if (student == null) {
			return null;
		}

		student.setName(model.getName());
		student.setSchool(model.getSchool());
		student.setAge(model.getAge());
		student.setGender(model.getGender());
		student.setDateOfBirth(model.getDateOfBirth());
		student.setPhone(model.getPhone());
		student.setAddress(model.getAddress());
		student.setJoiningDate(model.getJoiningDate());
		student.setActiveStatus(model.getActiveStatus());
		student.setModifiedDate(LocalDateTime.now());

		StandardEntity standard = standardRepo.findById(model.getStandardId()).orElse(null);
		student.setStandard(standard);

		MediumEntity medium = mediumRepo.findById(model.getMediumId()).orElse(null);
		student.setMedium(medium);

		StudentEntity updatedStudent = studentRepo.save(student);
		return mapToModel(updatedStudent);
	}

	@Override
	public String deleteStudent(Integer id) {
		studentRepo.deleteById(id);
		return "Student deleted successfully!";
	}

}
